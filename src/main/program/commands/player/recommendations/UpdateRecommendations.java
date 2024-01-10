package main.program.commands.player.recommendations;

import static main.program.Program.MAX_RESULTS;

import fileio.input.commands.UpdateRecommendationsInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Stream;
import lombok.Getter;
import main.program.commands.user.OnlineUserCommand;
import main.program.databases.Library;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.audio.files.Song;
import main.program.entities.users.User;
import main.program.entities.users.creators.Artist;
import main.program.entities.users.interactions.Player;

@Getter
public final class UpdateRecommendations extends OnlineUserCommand {

    private static final int MINIMUM_LISTEN_TIME = 30;
    private static final int[] SONGS_FROM_TOP_GENRES = {5, 3, 2};

    private final Builder resultBuilder = new Builder(this);
    private final String recommendationType;

    public UpdateRecommendations(final UpdateRecommendationsInput input) {
        super(input);
        recommendationType = input.getRecommendationType();
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
        return user.getRecommendations().setRecommendation(songs.get(songIndex));
    }

    private boolean getRandomPlaylist(final User user) {
        Iterator<String> genresIter = user.getTopGenres().limit(SONGS_FROM_TOP_GENRES.length)
            .iterator();
        // The user doesn't have any suggested genres, so we can't recommend anything.
        if (!genresIter.hasNext()) {
            return false;
        }

        List<Song> recommendedSongs = new ArrayList<>();

        for (int songsFromTopGenre : SONGS_FROM_TOP_GENRES) {
            if (!genresIter.hasNext()) {
                break;
            }

            String genre = genresIter.next();
            Stream<Song> songs = Library.getInstance().getSongs().stream()
                .filter(song -> song.getGenre().equals(genre))
                .sorted(Comparator.comparingInt(Song::getLikes).reversed());
            recommendedSongs.addAll(songs.limit(songsFromTopGenre).toList());
        }

        user.getRecommendations().setRecommendation(
            new Playlist(user + "'s recommendations", user, timestamp, recommendedSongs));
        return true;
    }

    private boolean getFanPlaylist(final User user) {
        Player player = user.getPlayer();
        Song nowPlaying = (Song) player.getPlayingAt(timestamp);
        if (nowPlaying == null) {
            return false;
        }
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
        return resultBuilder.returnMessage("No new recommendations were found");
    }
}
