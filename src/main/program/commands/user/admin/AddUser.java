package main.program.commands.user.admin;

import fileio.input.commands.AddUserInput;
import main.program.commands.DefaultOutputCommand;
import main.program.databases.UserDatabase;

public final class AddUser extends DefaultOutputCommand {

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
    public String returnExecutionMessage() {
        UserDatabase database = UserDatabase.getInstance();

        if (database.existsUser(user)) {
            return "The username " + user + " is already taken.";
        }

        database.addUser(type, user, age, city);
        return "The username " + user + " has been added successfully.";
    }
}
