package main.program.commands.user.merch;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.StatsResult;
import fileio.output.StatsResult.Builder;
import java.util.List;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.User;
import main.program.entities.users.creators.content.Merch;

@Getter
public final class SeeMerch extends Command {

    private final StatsResult.Builder resultBuilder = new Builder(this);

    public SeeMerch(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        List<String> merchNames = caller.getMerch().stream().map(Merch::getName).toList();
        return resultBuilder.result(merchNames).build();
    }
}
