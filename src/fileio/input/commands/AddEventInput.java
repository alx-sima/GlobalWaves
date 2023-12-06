package fileio.input.commands;

import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.user.artist.AddEvent;

@Getter
@Setter
public final class AddEventInput extends CommandInputWithName {

    private String description;
    private String date;

    @Override
    public Command createCommand() {
        return new AddEvent(this);
    }
}
