package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.StatsResult;
import fileio.output.StatsResult.Builder;
import java.util.List;
import lombok.Getter;
import main.entities.users.User;
import main.entities.users.creators.content.Merch;

@Getter
public final class SeeMerch extends OnlineUserCommand {

    private final StatsResult.Builder resultBuilder = new Builder(this);

    public SeeMerch(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
        List<String> merchNames = caller.getMerch().stream().map(Merch::getName).toList();
        return resultBuilder.result(merchNames).build();
    }
}
