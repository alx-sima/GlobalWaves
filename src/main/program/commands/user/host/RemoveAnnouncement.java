package main.program.commands.user.host;

import fileio.input.commands.CommandInputWithName;
import fileio.output.builders.ResultBuilder;
import java.util.List;
import main.entities.users.host.Announcement;
import main.entities.users.host.Host;

public final class RemoveAnnouncement extends HostCommand {

    private final String name;

    public RemoveAnnouncement(final CommandInputWithName input) {
        super(input);
        name = input.getName();
    }

    @Override
    protected ResultBuilder execute(final Host host) {
        List<Announcement> announcements = host.getAnnouncements();
        Announcement announcement = announcements.stream()
            .filter(a -> a.getName().equals(name)).findFirst().orElse(null);
        if (announcement == null) {
            return getResultBuilder().withMessage(
                user + " has no announcement with the given name.");
        }
        announcements.remove(announcement);
        return getResultBuilder().withMessage(user + " has successfully deleted the announcement.");
    }
}
