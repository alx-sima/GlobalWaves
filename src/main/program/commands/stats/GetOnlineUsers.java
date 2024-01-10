package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import fileio.output.StatsResult.Builder;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import main.program.entities.users.User;
import main.program.databases.UserDatabase;
import main.program.commands.Command;

@Getter
public final class GetOnlineUsers extends Command {

    private final StatsResult.Builder resultBuilder = new Builder(this);

    public GetOnlineUsers(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Stream<User> onlineUsers = UserDatabase.getInstance().getUsers().stream()
            .filter(User::isOnline);
        List<String> commandResult = onlineUsers.map(User::getUsername).toList();

        return resultBuilder.result(commandResult).build();
    }
}
