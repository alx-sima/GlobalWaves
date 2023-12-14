package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.builders.StatsResultBuilder;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import main.entities.users.User;
import main.entities.users.UserDatabase;
import main.program.commands.Command;

@Getter
public final class GetAllUsers extends Command {

    private final StatsResultBuilder resultBuilder = new StatsResultBuilder().withCommand(this);

    public GetAllUsers(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Stream<User> users = UserDatabase.getInstance().getAllUsers();
        List<String> commandResult = users.map(User::getUsername).toList();

        return resultBuilder.withResult(commandResult).build();
    }
}
