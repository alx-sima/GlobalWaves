package main.entities.users.host;

import main.entities.Searchable;
import main.entities.pages.HostPage;
import main.entities.users.User;

public final class Host extends User implements Searchable {

    public Host(final String type, final String username, final int age, final String city) {
        super(type, username, age, city);
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
