package main.entities.pages;

import java.util.List;
import main.entities.audio.collections.Podcast;
import main.entities.users.User;
import main.entities.users.host.Host;
import main.program.Library;

/**
 * A page that contains information about a host.
 */
public final class HostPage extends Page {

    private final Host host;

    public HostPage(final Host host) {
        super(null);
        this.host = host;
    }

    private List<Podcast> getPodcasts() {
        Library library = Library.getInstance();
        return library.getPodcasts().stream()
            .filter(podcast -> podcast.getOwner().equals(host.getUsername()))
            .toList();
    }

    @Override
    public String printPage() {
        return "Podcasts:\n\t" + getPodcasts() + "\n\nAnnouncements:\n\t" + host.getAnnouncements();
    }

    @Override
    public User getPageOwner() {
        return host;
    }
}
