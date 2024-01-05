package main.entities.users.host;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import main.entities.Searchable;
import main.entities.audio.files.Episode;
import main.entities.pages.HostPage;
import main.entities.users.CreatorWrapped;
import main.entities.users.User;

/**
 * A host, that can create podcasts and post announcements.
 */
public final class Host extends User implements Searchable {

    @Getter
    private final List<Announcement> announcements = new ArrayList<>();
    private final Wrapped wrapped = new Wrapped();

    public Host(final String username, final int age, final String city) {
        super(username, age, city);
    }

    @Override
    public boolean matchFilter(final String filter, final String parameter) {
        if (filter.equals("name")) {
            return username.startsWith(parameter);
        }
        return false;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String selectResultBy(final User user) {
        user.setCurrentPage(new HostPage(this));
        return username + "'s page";
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
