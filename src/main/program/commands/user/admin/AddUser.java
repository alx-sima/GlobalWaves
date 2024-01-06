package main.program.commands.user.admin;

import fileio.input.commands.AddUserInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.databases.UserDatabase;
import main.program.commands.Command;

public final class AddUser extends Command {

    @Getter
    private final MessageResult.Builder resultBuilder = new Builder(this);
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
        UserDatabase database = UserDatabase.getInstance();

        if (database.existsUser(user)) {
            resultBuilder.returnMessage("The username " + user + " is already taken.");
            return resultBuilder.build();
        }

        database.addUser(type, user, age, city);
        resultBuilder.returnMessage("The username " + user + " has been added successfully.");
        return resultBuilder.build();
    }
}
