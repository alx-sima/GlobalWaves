package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatusResult;
import fileio.output.StatusResult.Builder;
import lombok.Getter;
import main.entities.users.User;
import main.program.Player;
import main.program.commands.Command;

@Getter
public final class Status extends Command {

    private final StatusResult.Builder resultBuilder = new Builder(this);

    public Status(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        User caller = getCaller();
        Player player = caller.getPlayer();
        return resultBuilder.player(player, timestamp).build();
    }
}
