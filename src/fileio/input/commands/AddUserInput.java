package fileio.input.commands;

import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.user.admin.AddUser;

@Getter
@Setter
public final class AddUserInput extends CommandInput {

    private String type;
    private int age;
    private String city;

    @Override
    public Command createCommand() {
        return new AddUser(this);
    }
}
