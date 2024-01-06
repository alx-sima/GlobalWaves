package main.program.commands.user.artist;

import fileio.input.commands.CommandInputWithName;
import fileio.output.MessageResult;
import java.util.List;
import main.entities.users.creators.Artist;
import main.entities.users.creators.content.Event;

public final class RemoveEvent extends ArtistCommand {

    private final String name;

    public RemoveEvent(final CommandInputWithName input) {
        super(input);
        name = input.getName();
    }

    @Override
    protected MessageResult execute(final Artist artist) {
        List<Event> events = artist.getEvents();

        Event event = events.stream().filter(e -> e.getName().equals(name)).findFirst()
            .orElse(null);

        if (event == null) {
            return getResultBuilder().returnMessage(
                user + " doesn't have an event with the given name.");
        }

        events.remove(event);
        return getResultBuilder().returnMessage(user + " deleted the event successfully.");
    }
}
