package main.program.commands.user.artist;

import fileio.input.SongInput;
import fileio.input.commands.AddAlbumInput;
import java.util.List;
import main.program.entities.audio.collections.Album;
import main.program.entities.users.creators.Artist;

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
    protected String returnExecutionMessage(final Artist artist) {
        List<Album> albums = artist.getAlbums();

        if (albums.stream().anyMatch(album -> album.getName().equals(name))) {
            return user + " has another album with the same name.";
        }

        // Check for duplicate song names.
        if (songs.stream().map(SongInput::getName).distinct().count() != songs.size()) {
            return user + " has the same song at least twice in this album.";
        }

        artist.addAlbum(new Album(artist, name, releaseYear, description, songs, timestamp));

        return user + " has added new album successfully.";
    }
}
