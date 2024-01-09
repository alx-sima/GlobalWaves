package main.program.commands.user.admin;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.ExistsUser;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.audio.queues.visitors.OwnerVisitor;
import main.program.entities.users.User;
import main.program.databases.UserDatabase;

@Getter
public final class DeleteUser extends Command {

    private final MessageResult.Builder resultBuilder = new Builder(this);

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
    protected MessageResult execute() throws InvalidOperation {
        UserDatabase database = UserDatabase.getInstance();
        User target = new ExistsUser(user).check();

        for (User user : database.getUsers()) {
            user.getPlayer().updateTime(timestamp);
        }

        if (isBusyUser()) {
            return resultBuilder.returnMessage(user + " can't be deleted.");
        }

        database.removeUser(user);
        decreasePlaylistFollowersCount(target);
        removeTracesFromOtherUsers(target);

        return resultBuilder.returnMessage(user + " was successfully deleted.");

    }
}
