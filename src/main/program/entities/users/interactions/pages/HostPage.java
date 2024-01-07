package main.program.entities.users.interactions.pages;

import java.util.ArrayList;
import java.util.List;
import main.program.databases.Library;
import main.program.entities.audio.collections.Podcast;
import main.program.entities.users.creators.Host;
import main.program.entities.users.creators.content.Announcement;

/**
 * A page that contains information about a host.
 */
public final class HostPage extends Page {

    private final Host host;
    private final List<Podcast> podcasts;
    private final List<Announcement> announcements;

    public HostPage(final Host host) {
        super(null);
        this.host = host;
        podcasts = getPodcasts(host.getUsername());
        announcements = host.getAnnouncements();
    }

    public HostPage(final String hostname) {
        super(null);
        host = null;
        podcasts = getPodcasts(hostname);
        announcements = new ArrayList<>();
    }

    private static List<Podcast> getPodcasts(final String hostname) {
        Library library = Library.getInstance();
        return library.getPodcasts().stream()
            .filter(podcast -> podcast.getOwner().equals(hostname))
            .toList();
    }

    @Override
    public String printPage() {
        return "Podcasts:\n\t" + podcasts + "\n\nAnnouncements:\n\t" + announcements;
    }

    @Override
    public Host getPageOwner() {
        return host;
    }
}
