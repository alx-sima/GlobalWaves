package fileio.input.commands;

import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.search.Select;

@Getter
@Setter
public final class SelectInput extends CommandInput {

    private int itemNumber;

    public SelectInput() {
    }

    @Override
    public Command createCommand() {
        if (command.equals("select")) {
            return new Select(this);
        }

        return null;
    }
}
