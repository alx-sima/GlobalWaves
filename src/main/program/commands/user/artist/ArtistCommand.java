package main.program.commands.user.artist;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.builders.ResultBuilder;
import java.util.stream.Stream;
import lombok.Getter;
import main.entities.users.UserDatabase;
import main.entities.users.artist.Artist;
import main.program.commands.Command;

@Getter
public abstract class ArtistCommand extends Command {

    protected final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    protected ArtistCommand(final CommandInput input) {
        super(input);
    }

    private Artist getArtist() {
        Stream<Artist> artists = UserDatabase.getInstance().getArtists().stream();
        return artists.filter(artist -> user.equals(artist.getUsername())).findAny().orElse(null);
    }

    @Override
    public final CommandResult execute() {
        if (!UserDatabase.getInstance().existsUser(user)) {
            return getResultBuilder().withMessage("The username " + user + " doesn't exist.")
                .build();
        }

        Artist caller = getArtist();
        if (caller == null) {
            return getResultBuilder().withMessage(user + " is not an artist.").build();
        }

        return execute(caller).build();
    }

    /**
     * Executes the command as an artist.
     *
     * @param artist the artist that called the command.
     * @return the result of the command.
     */
    protected abstract ResultBuilder execute(Artist artist);
}
