package main.program.commands.user.artist;

import fileio.input.commands.CommandInputWithName;
import fileio.output.builders.ResultBuilder;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import main.entities.audio.collections.Album;
import main.entities.audio.collections.Playlist;
import main.entities.audio.files.Song;
import main.entities.users.User;
import main.entities.users.UserDatabase;
import main.entities.users.artist.Artist;

public final class RemoveAlbum extends ArtistCommand {

    private final String name;

    public RemoveAlbum(final CommandInputWithName input) {
        super(input);
        name = input.getName();
    }

    @Override
    protected ResultBuilder execute(final Artist artist) {
        List<User> users = UserDatabase.getInstance().getUsers();
        List<Album> albums = artist.getAlbums();
        Album album = albums.stream().filter(a -> a.getName().equals(name)).findFirst()
            .orElse(null);

        if (album == null) {
            return getResultBuilder().withMessage(
                user + " doesn't have an album with the given name.");
        }

        if (users.stream().map(u -> u.getPlayer().getPlayingAt(timestamp)).filter(Objects::nonNull)
            .anyMatch(file -> user.equals(file.getOwner()))) {
            return getResultBuilder().withMessage(user + " can't delete this album.");
        }

        Stream<Playlist> allUserPlaylists = users.stream()
            .flatMap(user -> user.getPlaylists().stream());

        Stream<Song> allPlaylistSongs = allUserPlaylists.flatMap(
            playlist -> playlist.getSongs().stream());

        if (allPlaylistSongs.anyMatch(song -> album.getSongs().contains(song))) {
            return getResultBuilder().withMessage(user + " can't delete this album.");
        }

        albums.remove(album);
        return getResultBuilder().withMessage(user + " deleted the album successfully.");
    }
}
