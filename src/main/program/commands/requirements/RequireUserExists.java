package main.program.commands.requirements;

import main.program.commands.exceptions.InvalidOperation;
import main.program.databases.UserDatabase;
import main.program.entities.users.User;

public final class RequireUserExists implements Requirement<User> {

    private final String username;

    public RequireUserExists(final String username) {
        this.username = username;
    }

    @Override
    public User check() throws InvalidOperation {
        User target = UserDatabase.getInstance().getUser(username);
        if (target != null) {
            return target;
        }

        throw new InvalidOperation("The username " + username + " doesn't exist.");
    }
}
