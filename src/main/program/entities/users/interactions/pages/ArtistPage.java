package main.program.entities.users.interactions.pages;

import java.util.List;
import main.program.entities.audio.collections.Album;
import main.program.entities.users.creators.Artist;

/**
 * A page that contains information about an artist.
 */
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
    public Artist getPageOwner() {
        return artist;
    }
}
