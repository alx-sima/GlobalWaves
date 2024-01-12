package main.program.commands.user.admin;

import fileio.input.commands.CommandInput;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.NoOutputCommand;
import main.program.databases.UserDatabase;

public final class AddUser extends NoOutputCommand {

    private final String type;
    private final int age;
    private final String city;

    public AddUser(final Input input) {
        super(input);
        type = input.getType();
        age = input.getAge();
        city = input.getCity();
    }

    @Override
    public String executeNoOutput() {
        UserDatabase database = UserDatabase.getInstance();

        if (database.existsUser(user)) {
            return "The username " + user + " is already taken.";
        }

        database.addUser(type, user, age, city);
        return "The username " + user + " has been added successfully.";
    }

    @Getter
    @Setter
    public static final class Input extends CommandInput {

        private String type;
        private int age;
        private String city;

        @Override
        public Command createCommand() {
            return new AddUser(this);
        }
    }
}
