package main.entities.users.artist;

import main.entities.Searchable;
import main.entities.users.User;

public class Artist extends User implements Searchable {

    public Artist(final String type, final String username, final int age, final String city) {
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
    public void selectResultBy(final User user) {
        // TODO
    }

}
