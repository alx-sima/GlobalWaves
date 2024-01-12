package main.program.commands.user.artist;

import fileio.input.commands.CommandWithNameInput;
import java.util.List;
import main.program.entities.users.creators.Artist;
import main.program.entities.users.creators.content.Event;

public final class RemoveEvent extends ArtistCommand {

    private final String name;

    public RemoveEvent(final CommandWithNameInput input) {
        super(input);
        name = input.getName();
    }

    @Override
    protected String returnExecutionMessage(final Artist artist) {
        List<Event> events = artist.getEvents();

        Event event = events.stream().filter(e -> e.getName().equals(name)).findFirst()
            .orElse(null);

        if (event == null) {
            return user + " doesn't have an event with the given name.";
        }

        events.remove(event);
        return user + " deleted the event successfully.";
    }
}
