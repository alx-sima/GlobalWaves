package fileio.input.commands;

import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.user.host.AddAnnouncement;

@Getter
@Setter
public final class AddAnnouncementInput extends CommandInputWithName {

    private String description;

    @Override
    public Command createCommand() {
        return new AddAnnouncement(this);
    }
}
