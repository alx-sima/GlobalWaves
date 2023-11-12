package main.commands;

import main.Program;
import main.audio.Player;
import main.audio.PlayerStatus;

public final class Status extends Command {
    public Status(final String command, final String user, final int timestamp) {
        super(command, user, timestamp);
    }

    @Override
    public Result execute() {
        Program instance = Program.getInstance();
        Player player = instance.getPlayer();
        return new Result(this, new PlayerStatus(player, getTimestamp()));
    }
}
