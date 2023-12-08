package main.program.commands.user.artist;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import java.util.Map;
import main.program.Program;
import main.program.User;
import main.program.commands.Command;

public abstract class ArtistCommand extends Command {


    protected ArtistCommand(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        ResultBuilder resultBuilder = new MessageResultBuilder(this);

        Program program = Program.getInstance();
        Map<String, User> artists = program.getDatabase().getArtists();

        if (!program.getDatabase().existsUser(user)) {
            resultBuilder.withMessage("The username " + user + " doesn't exist.");
            return resultBuilder.build();
        }
        if (!artists.containsKey(user)) {
            resultBuilder.withMessage(user + " is not an artist.");
            return resultBuilder.build();
        }

        return executeAsArtist().build();
    }

    protected abstract ResultBuilder executeAsArtist();
}
