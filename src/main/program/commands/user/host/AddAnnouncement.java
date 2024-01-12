package main.program.commands.user.host;

import fileio.input.commands.CommandWithNameInput;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.entities.users.creators.Host;
import main.program.entities.users.creators.content.Announcement;

public final class AddAnnouncement extends HostCommand {

    private final String name;
    private final String description;

    public AddAnnouncement(final Input input) {
        super(input);
        name = input.getName();
        description = input.getDescription();
    }

    @Override
    protected String returnExecutionMessage(final Host host) {
        host.addAnnouncement(new Announcement(user, name, description));
        return user + " has successfully added new announcement.";
    }


    @Getter
    @Setter
    public static final class Input extends CommandWithNameInput {

        private String description;

        @Override
        public Command createCommand() {
            return new AddAnnouncement(this);
        }
    }
}
