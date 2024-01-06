package main.entities.users.creators;

import lombok.Getter;
import main.entities.Searchable;
import main.entities.users.User;
import main.program.notifications.Notifier;

@Getter
public abstract class Creator extends User implements Searchable {

    private final Notifier notifier = new Notifier();

    protected Creator(String username, int age, String city) {
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
}
