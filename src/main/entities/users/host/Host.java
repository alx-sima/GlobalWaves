package main.entities.users.host;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import main.entities.Searchable;
import main.entities.pages.HostPage;
import main.entities.users.User;

/**
 * A host, that can create podcasts and post announcements.
 */
@Getter
public final class Host extends User implements Searchable {

    private final List<Announcement> announcements = new ArrayList<>();

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
}
