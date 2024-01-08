package main.program.commands.user.artist;

import fileio.input.commands.CommandInput;
import java.util.stream.Stream;
import lombok.Getter;
import main.program.commands.DefaultOutputCommand;
import main.program.databases.UserDatabase;
import main.program.entities.users.creators.Artist;

/**
 * A command that can be executed only by artists.
 */
@Getter
public abstract class ArtistCommand extends DefaultOutputCommand {

    protected ArtistCommand(final CommandInput input) {
        super(input);
    }

    private Artist getArtist() {
        Stream<Artist> artists = UserDatabase.getInstance().getArtists().stream();
        return artists.filter(artist -> user.equals(artist.getUsername())).findAny().orElse(null);
    }

    @Override
    protected final String returnExecutionMessage() {
        if (!UserDatabase.getInstance().existsUser(user)) {
            return "The username " + user + " doesn't exist.";
        }

        Artist caller = getArtist();
        if (caller == null) {
            return user + " is not an artist.";
        }

        return returnExecutionMessage(caller);
    }

    /**
     * Executes the command as an artist.
     *
     * @param artist the artist that called the command.
     * @return the result of the command.
     */
    protected abstract String returnExecutionMessage(Artist artist);
}
