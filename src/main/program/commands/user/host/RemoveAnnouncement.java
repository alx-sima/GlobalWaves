package main.program.commands.user.host;

import fileio.input.commands.CommandInputWithName;
import java.util.List;
import main.program.entities.users.creators.Host;
import main.program.entities.users.creators.content.Announcement;

public final class RemoveAnnouncement extends HostCommand {

    private final String name;

    public RemoveAnnouncement(final CommandInputWithName input) {
        super(input);
        name = input.getName();
    }

    @Override
    protected String returnExecutionMessage(final Host host) {
        List<Announcement> announcements = host.getAnnouncements();
        Announcement announcement = announcements.stream()
            .filter(a -> a.getName().equals(name)).findFirst().orElse(null);
        if (announcement == null) {
            return user + " has no announcement with the given name.";
        }

        announcements.remove(announcement);
        return user + " has successfully deleted the announcement.";
    }
}
