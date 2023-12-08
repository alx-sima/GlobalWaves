package main.program.commands.user.admin;

import fileio.input.commands.AddUserInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import java.util.Map;
import main.program.Program;
import main.program.User;
import main.program.commands.Command;

public final class AddUser extends Command {

    private final String type;
    private final int age;
    private final String city;

    public AddUser(final AddUserInput input) {
        super(input);
        type = input.getType();
        age = input.getAge();
        city = input.getCity();
    }

    @Override
    public CommandResult execute() {

        Program program = Program.getInstance();
        Map<String, User> users = program.getUsers();

        if (users.containsKey(user)) {
            return new MessageResult(this, "The username " + user + " is already taken.");
        }

        User newUser = new User(type, user, age, city);
        users.put(user, newUser);
        return new MessageResult(this, "The username " + user + " has been added successfully.");
    }
}
