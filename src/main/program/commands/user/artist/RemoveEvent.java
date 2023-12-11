package main.program.commands.user.artist;

import fileio.input.commands.CommandInputWithName;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.entities.users.artist.Event;
import main.program.Library;
import main.program.Program;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.IsArtistDependency;

public final class RemoveEvent extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final String name;

    public RemoveEvent(final CommandInputWithName input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        name = input.getName();
    }

    @Override
    public CommandResult checkDependencies() {
        IsArtistDependency dependency = new IsArtistDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Library library = Program.getInstance().getLibrary();
        Event event = library.getEvents().stream()
            .filter(e -> e.getName().equals(name)).findFirst().orElse(null);

        if (event == null) {
            return resultBuilder.withMessage(user + " doesn't have an event with the given name.");
        }

        library.getEvents().remove(event);
        return resultBuilder.withMessage(user + " deleted the event successfully.");
    }
}
