package main.entities.pages;

import java.util.List;
import main.entities.audio.collections.Podcast;
import main.entities.users.User;
import main.entities.users.host.Announcement;
import main.entities.users.host.Host;
import main.program.Library;
import main.program.Program;

public final class HostPage implements Page {

    private final Host host;

    public HostPage(final Host host) {
        this.host = host;
    }

    private List<Podcast> getPodcasts() {
        Library library = Program.getInstance().getLibrary();
        return library.getPodcasts().stream()
            .filter(podcast -> podcast.getOwner().equals(host.getUsername()))
            .toList();

    }

    private List<Announcement> getAnnouncements() {
        Library library = Program.getInstance().getLibrary();
        return library.getAnnouncements().stream()
            .filter(announcement -> announcement.getOwner().equals(host.getUsername())).toList();
    }

    @Override
    public String printPageOfUser(final User user) {
        return "Podcasts:\n\t" + getPodcasts() + "\n\nAnnouncements:\n\t" + getAnnouncements();
    }

    @Override
    public User getPageOwner() {
        return host;
    }
}
