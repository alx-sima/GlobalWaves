package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import main.program.Program;
import main.entities.users.User;
import main.program.commands.Command;

public final class GetAllUsers extends Command {

    public GetAllUsers(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program program = Program.getInstance();
        Stream<User> users = program.getDatabase().getUsers().values().stream().sorted(
            Comparator.comparing(User::getUsername));
        List<String> commandResult = users.map(User::getUsername).toList();

        return new StatsResult(this, commandResult);
    }
}
