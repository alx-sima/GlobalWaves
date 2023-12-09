package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import java.util.List;
import java.util.stream.Stream;
import main.entities.users.User;
import main.program.Program;
import main.program.commands.Command;

public final class GetAllUsers extends Command {

    public GetAllUsers(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program program = Program.getInstance();
        Stream<User> users = program.getDatabase().getAllUsers();
        List<String> commandResult = users.map(User::getUsername).toList();

        return new StatsResult(this, commandResult);
    }
}
