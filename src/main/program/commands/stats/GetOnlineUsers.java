package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import main.program.Program;
import main.program.User;
import main.program.commands.Command;

public final class GetOnlineUsers extends Command {

    public GetOnlineUsers(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program program = Program.getInstance();
        Stream<User> onlineUsers = program.getUsers().values().stream().filter(User::isOnline)
            .sorted(
                Comparator.comparing(User::getUsername));
        List<String> commandResult = onlineUsers.map(User::getUsername).toList();

        return new StatsResult(this, commandResult);
    }
}
