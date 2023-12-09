package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.audio.queues.Queue;
import main.audio.queues.RepeatMode;
import main.program.Player;
import main.program.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class Repeat extends DependentCommand {

    private final MessageResultBuilder resultBuilder;

    public Repeat(final CommandInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
    }

    @Override
    public CommandResult checkDependencies() {
        OnlineUserDependency dependency = new OnlineUserDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        User caller = getCaller();
        Player player = caller.getPlayer();
        Queue queue = player.getQueue();

        if (queue == null) {
            return resultBuilder.withMessage(
                "Please load a source before setting the repeat status.");
        }

        player.updateTime(timestamp);
        RepeatMode newMode = queue.changeRepeatMode();
        return resultBuilder.withMessage(
            "Repeat mode changed to " + newMode.toString().toLowerCase() + ".");
    }
}
