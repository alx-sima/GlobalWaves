package main.program.entities.users.creators;

import fileio.output.wrapped.HostWrapped;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import main.program.entities.users.interactions.pages.HostPage;
import main.program.entities.users.User;
import main.program.entities.users.creators.content.Announcement;
import main.program.commands.exceptions.InvalidOperation;
import main.program.entities.users.interactions.notifications.Notification;
import main.program.entities.users.interactions.notifications.Notifier;

/**
 * A host, that can create podcasts and post announcements.
 */
@Getter
public final class Host extends Creator {

    private final List<Announcement> announcements = new ArrayList<>();
    private final Notifier notifier = new Notifier();

    public Host(final String username, final int age, final String city) {
        super(username, age, city);
    }

    @Override
    public String selectResultBy(final User user) {
        user.setCurrentPage(new HostPage(this));
        return username + "'s page";
    }

    /**
     * Add a new announcement to the host.
     */
    public void addAnnouncement(final Announcement announcement) {
        announcements.add(announcement);

        notifier.updateSubscribers(
            new Notification("New Announcement", "New Announcement from " + username + "."));
    }

    @Override
    public HostWrapped getWrapped() {
        return new HostWrapped(this);
    }

    @Override
    public boolean buyMerch(final User buyer, final String merchName) throws InvalidOperation {
        // A host doesn't sell merch.
        throw new InvalidOperation();
    }
}
