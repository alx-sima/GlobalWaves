package main.program.commands.user.admin;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import main.entities.audio.collections.Playlist;
import main.entities.audio.queues.visitors.OwnerVisitor;
import main.entities.users.User;
import main.entities.users.UserDatabase;
import main.program.Library;
import main.program.commands.user.UserCommand;

@Getter
public final class DeleteUser extends UserCommand {

    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    public DeleteUser(final CommandInput input) {
        super(input);
    }

    private boolean isBusyUser() {
        List<User> users = UserDatabase.getInstance().getUsers();

        if (users.stream().map(user -> user.getPlayer().getPlayingAt(timestamp))
            .filter(Objects::nonNull).anyMatch(song -> song.getOwner().equals(user))) {
            return true;
        }

        OwnerVisitor visitor = new OwnerVisitor(user);
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

    private void decreasePlaylistFollowersCount(final User target) {
        target.getFollowedPlaylists()
            .forEach(playlist -> playlist.setFollowers(playlist.getFollowers() - 1));
    }

    private void removeTracesFromOtherUsers(final User target) {
        for (User u : UserDatabase.getInstance().getUsers()) {
            u.getLikedSongs().removeIf(song -> song.getOwner().equals(user));

            List<Playlist> playlistsToRemove = u.getFollowedPlaylists().stream()
                .filter(playlist -> playlist.getUser().equals(target)).toList();
            for (Playlist playlist : playlistsToRemove) {
                u.follow(playlist);
            }
        }
    }

    @Override
    protected ResultBuilder executeFor(final User target) {
        Library library = Library.getInstance();
        UserDatabase database = UserDatabase.getInstance();

        for (User user : database.getUsers()) {
            user.getPlayer().updateTime(timestamp);
        }

        if (isBusyUser()) {
            return resultBuilder.withMessage(user + " can't be deleted.");
        }

        database.removeUser(user);
        library.getSongs().removeIf(song -> song.getOwner().equals(user));
        decreasePlaylistFollowersCount(target);
        removeTracesFromOtherUsers(target);

        return resultBuilder.withMessage(user + " was successfully deleted.");

    }
}
