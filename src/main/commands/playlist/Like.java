package main.commands.playlist;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.Program;
import main.User;
import main.audio.collections.Queue;
import main.commands.Command;

public class Like extends Command {

    public Like(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());
        Queue queue = instance.getPlayer().getQueue();

        if (queue == null) {
            return new MessageResult(this, "Please load a source before liking or unliking.");
        }

        if (user.like(queue.getPlayingSong())) {
            return new MessageResult(this, "Like registered successfully.");
        }
        return new MessageResult(this, "Unlike registered successfully.");
    }
}
