package main.program.commands.user.artist;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import java.util.stream.Stream;
import lombok.Getter;
import main.program.databases.UserDatabase;
import main.program.entities.users.creators.Artist;
import main.program.commands.Command;

/**
 * A command that can be executed only by artists.
 */
@Getter
public abstract class ArtistCommand extends Command {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    protected ArtistCommand(final CommandInput input) {
        super(input);
    }

    private Artist getArtist() {
        Stream<Artist> artists = UserDatabase.getInstance().getArtists().stream();
        return artists.filter(artist -> user.equals(artist.getUsername())).findAny().orElse(null);
    }

    @Override
    public final MessageResult execute() {
        if (!UserDatabase.getInstance().existsUser(user)) {
            return getResultBuilder().returnMessage("The username " + user + " doesn't exist.");
        }

        Artist caller = getArtist();
        if (caller == null) {
            return getResultBuilder().returnMessage(user + " is not an artist.");
        }

        return execute(caller);
    }

    /**
     * Executes the command as an artist.
     *
     * @param artist the artist that called the command.
     * @return the result of the command.
     */
    protected abstract MessageResult execute(Artist artist);
}
