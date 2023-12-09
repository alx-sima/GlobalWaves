package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import java.util.List;
import java.util.stream.Stream;
import main.entities.users.User;
import main.program.Program;
import main.program.commands.Command;

public final class GetOnlineUsers extends Command {

    public GetOnlineUsers(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program program = Program.getInstance();
        Stream<User> onlineUsers = program.getDatabase().getUsers().stream()
            .filter(User::isOnline);
        List<String> commandResult = onlineUsers.map(User::getUsername).toList();

        return new StatsResult(this, commandResult);
    }
}
