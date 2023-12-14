package main.entities.pages;

import java.util.List;
import main.entities.audio.collections.Album;
import main.entities.users.User;
import main.entities.users.artist.Artist;

public final class ArtistPage extends Page {

    private final Artist artist;

    public ArtistPage(final Artist artist) {
        super(null);
        this.artist = artist;
    }

    private List<String> getAlbums() {
        return artist.getAlbums().stream().map(Album::getName).toList();
    }

    @Override
    public String printPage() {
        return "Albums:\n\t" + getAlbums() + "\n\nMerch:\n\t" + artist.getMerch()
            + "\n\nEvents:\n\t" + artist.getEvents();
    }

    @Override
    public User getPageOwner() {
        return artist;
    }
}
