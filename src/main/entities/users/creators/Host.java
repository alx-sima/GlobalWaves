package main.entities.users.creators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import main.entities.audio.files.Episode;
import main.entities.pages.HostPage;
import main.entities.users.User;
import main.entities.users.creators.content.Announcement;
import main.program.notifications.Notification;
import main.program.notifications.Notifier;

/**
 * A host, that can create podcasts and post announcements.
 */
public final class Host extends Creator {

    @Getter
    private final List<Announcement> announcements = new ArrayList<>();
    private final Wrapped wrapped = new Wrapped();
    @Getter
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

    /**
     * Record a listen by `listener` to the `episode`.
     */
    public void addListen(final User listener, final Episode episode) {
        wrapped.addListen(listener, episode);
        listener.addListen(episode);
    }

    @Getter
    public static final class Wrapped implements CreatorWrapped<Episode> {

        private final Map<Episode, Integer> topEpisodes = new HashMap<>();
        private final Map<User, Integer> topListeners = new HashMap<>();

        @Override
        public void addListen(final User listener, final Episode listened) {
            CreatorWrapped.increment(topEpisodes, listened);
            CreatorWrapped.increment(topListeners, listener);
        }
    }
}
