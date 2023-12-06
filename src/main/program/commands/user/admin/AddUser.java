package main.program.commands.user.admin;

import fileio.input.commands.AddUserInput;
import fileio.output.CommandResult;
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
        // TODO
        return null;
    }
}
