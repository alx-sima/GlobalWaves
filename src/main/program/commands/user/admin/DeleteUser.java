package main.program.commands.user.admin;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.entities.users.User;
import main.entities.users.UserDatabase;
import main.program.Library;
import main.program.Program;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.ExistsUserDependency;

public final class DeleteUser extends DependentCommand {

    private final MessageResultBuilder resultBuilder;

    public DeleteUser(final CommandInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
    }

    @Override
    public CommandResult checkDependencies() {
        ExistsUserDependency dependency = new ExistsUserDependency(this, resultBuilder);
        return dependency.checkDependencies();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        UserDatabase database = Program.getInstance().getDatabase();
        Library library = Program.getInstance().getLibrary();

        for (User user : database.getUsers()) {
            user.getPlayer().updateTime(timestamp);
        }

        if (database.getBusyUsers().contains(user)) {
            return resultBuilder.withMessage(user + " can't be deleted.");
        }

        database.getUsers().remove(getCaller());
        database.getArtists().remove(getCaller());
        database.getHosts().remove(getCaller());
        library.getAlbums().removeIf(album -> album.getOwner().equals(user));
        library.getMerch().removeIf(merch -> merch.getOwner().equals(user));
        library.getEvents().removeIf(event -> event.getOwner().equals(user));
//        library.getSongs().removeIf(song -> song.getOwner().equals(user));
        for (User u : database.getUsers()) {
            u.getLikedSongs().removeIf(song -> song.getOwner().equals(user));
        }
        return resultBuilder.withMessage(user + " was successfully deleted.");

    }
}
