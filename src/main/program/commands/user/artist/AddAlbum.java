package main.program.commands.user.artist;

import fileio.input.SongInput;
import fileio.input.commands.AddAlbumInput;
import fileio.output.MessageResult;
import java.util.List;
import main.entities.audio.collections.Album;
import main.entities.users.artist.Artist;
import main.program.Library;

public final class AddAlbum extends ArtistCommand {

    private final String name;
    private final int releaseYear;
    private final String description;
    private final List<SongInput> songs;

    public AddAlbum(final AddAlbumInput input) {
        super(input);
        name = input.getName();
        releaseYear = input.getReleaseYear();
        description = input.getDescription();
        songs = input.getSongs();
    }

    @Override
    protected MessageResult execute(final Artist artist) {
        List<Album> albums = artist.getAlbums();

        if (albums.stream().anyMatch(album -> album.getName().equals(name))) {
            return getResultBuilder().returnMessage(
                user + " has another album with the same name.");
        }

        // Check for duplicate song names.
        if (songs.stream().map(SongInput::getName).distinct().count() != songs.size()) {
            return getResultBuilder().returnMessage(
                user + " has the same song at least twice in this album.");
        }

        Album album = new Album(artist, name, releaseYear, description, songs, timestamp);
        Library.getInstance().getSongs().addAll(album.getSongs());
        albums.add(album);

        return getResultBuilder().returnMessage(user + " has added new album successfully.");
    }
}
