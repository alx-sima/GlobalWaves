package main.entities.users.creators;

import lombok.Getter;
import main.entities.Searchable;
import main.entities.users.User;
import main.program.InvalidOperation;
import main.program.notifications.Notifier;

@Getter
public abstract class Creator extends User implements Searchable {

    private final Notifier notifier = new Notifier();

    protected Creator(final String username, final int age, final String city) {
        super(username, age, city);
    }

    @Override
    public final boolean matchFilter(final String filter, final String parameter) {
        if (filter.equals("name")) {
            return username.startsWith(parameter);
        }
        return false;
    }

    @Override
    public final String getName() {
        return username;
    }

    /**
     * Buy merch from the creator.
     *
     * @param buyer     the user which buys the merch.
     * @param merchName the name of the merch.
     * @return true if merch was bought, false if the merch doesn't exist.
     * @throws InvalidOperation the creator doesn't sell merch.
     */
    public abstract boolean buyMerch(User buyer, String merchName)
        throws InvalidOperation;
}
