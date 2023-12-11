package main.entities.pages;

import java.util.List;
import main.entities.audio.collections.Album;
import main.program.Library;
import main.entities.users.User;
import main.entities.users.artist.Artist;
import main.entities.users.artist.Event;
import main.entities.users.artist.Merch;
import main.program.Program;

public final class ArtistPage implements Page {

    private final Artist artist;

    public ArtistPage(final Artist artist) {
        this.artist = artist;
    }

    private List<String> getAlbums() {
        Library library = Program.getInstance().getLibrary();
        return library.getAlbums().stream()
            .filter(album -> album.getOwner().equals(artist.getUsername())).map(Album::getName)
            .toList();
    }

    private List<Merch> getMerch() {
        Library library = Program.getInstance().getLibrary();
        return library.getMerch().stream()
            .filter(merch -> merch.getOwner().equals(artist.getUsername())).toList();
    }

    private List<Event> getEvents() {
        Library library = Program.getInstance().getLibrary();
        return library.getEvents().stream()
            .filter(event -> event.getOwner().equals(artist.getUsername())).toList();
    }

    @Override
    public String printPageOfUser(final User user) {
        return "Albums:\n\t" + getAlbums() + "\n\nMerch:\n\t" + getMerch()
            + "\n\nEvents:\n\t" + getEvents();
    }

    @Override
    public User getPageOwner() {
        return artist;
    }
}
