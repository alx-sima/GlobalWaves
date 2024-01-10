package main.program.commands.user.host;

import fileio.input.commands.AddAnnouncementInput;
import main.program.entities.users.creators.Host;
import main.program.entities.users.creators.content.Announcement;

public final class AddAnnouncement extends HostCommand {

    private final String name;
    private final String description;

    public AddAnnouncement(final AddAnnouncementInput input) {
        super(input);
        name = input.getName();
        description = input.getDescription();
    }

    @Override
    protected String returnExecutionMessage(final Host host) {
        host.addAnnouncement(new Announcement(user, name, description));
        return user + " has successfully added new announcement.";
    }
}
