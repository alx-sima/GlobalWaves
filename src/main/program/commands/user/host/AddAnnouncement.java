package main.program.commands.user.host;

import fileio.input.commands.AddAnnouncementInput;
import fileio.output.builders.ResultBuilder;
import java.util.List;
import main.entities.users.host.Announcement;
import main.entities.users.host.Host;

public final class AddAnnouncement extends HostCommand {

    private final String name;
    private final String description;

    public AddAnnouncement(final AddAnnouncementInput input) {
        super(input);
        name = input.getName();
        description = input.getDescription();
    }

    @Override
    protected ResultBuilder execute(final Host host) {
        List<Announcement> announcements = host.getAnnouncements();
        announcements.add(new Announcement(user, name, description));
        return getResultBuilder().withMessage(user + " has successfully added new announcement.");
    }
}
