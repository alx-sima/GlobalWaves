package main.program.commands.user.host;

import fileio.input.commands.AddAnnouncementInput;
import fileio.output.MessageResult;
import main.entities.users.creators.Host;
import main.entities.users.creators.content.Announcement;

public final class AddAnnouncement extends HostCommand {

    private final String name;
    private final String description;

    public AddAnnouncement(final AddAnnouncementInput input) {
        super(input);
        name = input.getName();
        description = input.getDescription();
    }

    @Override
    protected MessageResult execute(final Host host) {
        host.addAnnouncement(new Announcement(user, name, description));
        return getResultBuilder().returnMessage(user + " has successfully added new announcement.");
    }
}
