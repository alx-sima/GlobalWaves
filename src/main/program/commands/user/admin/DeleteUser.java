package main.program.commands.user.admin;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import java.util.List;
import java.util.Objects;
import main.entities.audio.collections.Playlist;
import main.entities.audio.queues.visitors.QueueVisitor;
import main.entities.users.User;
import main.entities.users.UserDatabase;
import main.program.Library;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.ExistsUserDependency;

public final class DeleteUser extends DependentCommand {

    private final MessageResultBuilder resultBuilder;

    public DeleteUser(final CommandInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
    }

    private boolean isBusyUser() {
        List<User> users = UserDatabase.getInstance().getUsers();

        if (users.stream().map(user -> user.getPlayer().getPlayingAt(timestamp))
            .filter(Objects::nonNull).anyMatch(song -> song.getOwner().equals(user))) {
            return true;
        }

        QueueVisitor visitor = new QueueVisitor(user);
        if (users.stream().map(user -> user.getPlayer().getQueue()).filter(Objects::nonNull)
            .anyMatch(queue -> {
                queue.accept(visitor);
                return visitor.isOwned();
            })) {
            return true;
        }

        return users.stream().map(user -> user.getCurrentPage().getPageOwner())
            .filter(Objects::nonNull)
            .anyMatch(owner -> owner.equals(getCaller()));
    }

    @Override
    public CommandResult checkDependencies() {
        ExistsUserDependency dependency = new ExistsUserDependency(this, resultBuilder);
        return dependency.checkDependencies();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Library library = Library.getInstance();
        UserDatabase database = UserDatabase.getInstance();

        for (User user : database.getUsers()) {
            user.getPlayer().updateTime(timestamp);
        }

        if (isBusyUser()) {
            return resultBuilder.withMessage(user + " can't be deleted.");
        }

        User caller = getCaller();

        database.getUsers().remove(caller);
        database.getArtists().remove(caller);
        database.getHosts().remove(caller);
        library.getAlbums().removeIf(album -> album.getOwner().equals(user));
        library.getMerch().removeIf(merch -> merch.getOwner().equals(user));
        library.getEvents().removeIf(event -> event.getOwner().equals(user));
        library.getSongs().removeIf(song -> song.getOwner().equals(user));
        caller.getFollowedPlaylists()
            .forEach(playlist -> playlist.setFollowers(playlist.getFollowers() - 1));
        for (User u : database.getUsers()) {
            u.getLikedSongs().removeIf(song -> song.getOwner().equals(user));

            List<Playlist> playlistsToRemove = u.getFollowedPlaylists().stream()
                .filter(playlist -> playlist.getUser().equals(caller)).toList();
            for (Playlist playlist : playlistsToRemove) {
                u.follow(playlist);
            }
        }
        return resultBuilder.withMessage(user + " was successfully deleted.");

    }
}
