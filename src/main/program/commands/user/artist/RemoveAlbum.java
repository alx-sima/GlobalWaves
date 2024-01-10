package main.program.commands.user.artist;

import fileio.input.commands.CommandInputWithName;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import main.program.databases.Library;
import main.program.databases.UserDatabase;
import main.program.entities.audio.collections.Album;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.audio.files.Song;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;
import main.program.entities.users.creators.Artist;

public final class RemoveAlbum extends ArtistCommand {

    private final String name;

    public RemoveAlbum(final CommandInputWithName input) {
        super(input);
        name = input.getName();
    }

    @Override
    protected String returnExecutionMessage(final Artist artist) {
        List<User> users = UserDatabase.getInstance().getUsers();
        List<Album> albums = artist.getAlbums();
        Album album = albums.stream().filter(a -> a.getName().equals(name)).findFirst()
            .orElse(null);

        if (album == null) {
            return user + " doesn't have an album with the given name.";
        }

        if (users.stream().map(u -> {
                u.getPlayer().updateTime(timestamp);
                return u.getPlayer().getQueue();
            }).filter(Objects::nonNull).map(Queue::getCurrentSong).filter(Objects::nonNull)
            .anyMatch(file -> file.getAlbum().getName().equals(name))) {
            return user + " can't delete this album.";
        }

        Stream<Playlist> allUserPlaylists = users.stream()
            .flatMap(user -> user.getPlaylists().stream());

        Stream<Song> allPlaylistSongs = allUserPlaylists.flatMap(
            playlist -> playlist.getSongs().stream());

        if (allPlaylistSongs.anyMatch(song -> album.getSongs().contains(song))) {
            return user + " can't delete this album.";
        }

        Library.getInstance().getSongs().removeIf(song -> album.getSongs().contains(song));
        albums.remove(album);

        return user + " deleted the album successfully.";
    }
}
