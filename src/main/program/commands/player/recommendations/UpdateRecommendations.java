package main.program.commands.player.recommendations;

import static main.program.Program.MAX_RESULTS;

import fileio.input.commands.UpdateRecommendationsInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Stream;
import lombok.Getter;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.audio.files.Song;
import main.program.entities.users.User;
import main.program.entities.users.creators.Artist;
import main.program.databases.Library;
import main.program.entities.users.interactions.Player;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class UpdateRecommendations extends OnlineUserCommand {

    private static final int MINIMUM_LISTEN_TIME = 30;
    private final Builder resultBuilder = new Builder(this);
    private final String recommendationType;

    public UpdateRecommendations(final UpdateRecommendationsInput input) {
        super(input);
        recommendationType = input.getRecommendationType();
    }

    private static Stream<Song> getTopSongs(final User user) {
        return user.getLikedSongs().stream()
            .sorted(Comparator.comparingInt(Song::getLikes).reversed()).limit(MAX_RESULTS);
    }

    private boolean getRandomSong(final User user) {
        Player player = user.getPlayer();

        // TODO
        Song nowPlaying = (Song) player.getPlayingAt(timestamp);
        if (nowPlaying == null) {
            return false;
        }

        int remainingTime = player.remainingTimeAt(timestamp);
        int listenTime = nowPlaying.getDuration() - remainingTime;
        if (listenTime < MINIMUM_LISTEN_TIME) {
            return false;
        }

        List<Song> songs = Library.getInstance().getSongs();
        songs = songs.stream().filter(song -> song.getGenre().equals(nowPlaying.getGenre()))
            .toList();

        int songIndex = new Random(listenTime).nextInt(0, songs.size());
        user.getRecommendations().setRecommendation(songs.get(songIndex));
        return true;
    }

    private boolean getRandomPlaylist(final User user) {
        return false;
    }

    private boolean getFanPlaylist(final User user) {
        Player player = user.getPlayer();
        Song nowPlaying = (Song) player.getPlayingAt(timestamp);
        Artist artist = nowPlaying.getArtist();

        List<User> fans = artist.getListeners().entrySet().stream().sorted(
                Entry.<User, Integer>comparingByValue().reversed()).limit(MAX_RESULTS)
            .map(Entry::getKey).toList();

        List<Song> songs = fans.stream()
            .flatMap(fan -> fan.getLikedSongs().stream().limit(MAX_RESULTS)).toList();

        Playlist newPlaylist = new Playlist(artist.getUsername() + " Fan Club recommendations",
            user, timestamp, songs);
        user.getRecommendations().setRecommendation(newPlaylist);
        return true;
    }

    @Override
    protected MessageResult execute(final User caller) {
        boolean commandStatus = switch (recommendationType) {
            case "random_song" -> getRandomSong(caller);
            case "random_playlist" -> getRandomPlaylist(caller);
            case "fans_playlist" -> getFanPlaylist(caller);
            default -> false;
        };

        if (commandStatus) {
            return resultBuilder.returnMessage(
                "The recommendations for user " + user + " have been updated successfully.");
        }
        return resultBuilder.returnMessage("No new recommendations were found.");
    }
}
