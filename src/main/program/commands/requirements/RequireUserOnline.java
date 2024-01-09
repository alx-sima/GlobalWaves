package main.program.commands.requirements;

import main.program.commands.exceptions.InvalidOperation;
import main.program.entities.users.User;

public final class RequireUserOnline implements Requirement<User> {

    private final String username;

    public RequireUserOnline(String username) {
        this.username = username;
    }

    @Override
    public User check() throws InvalidOperation {
        User target = new ExistsUser(username).check();
        if (target.isOnline()) {
            return target;
        }

        throw new InvalidOperation("The user " + username + " is offline.");
    }
}
