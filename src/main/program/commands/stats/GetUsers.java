package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import fileio.output.StatsResult.Builder;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.databases.UserDatabase;
import main.program.entities.users.User;

@Getter
public final class GetUsers extends Command {

    private final StatsResult.Builder resultBuilder = new Builder(this);
    private final boolean filterOnlineUsers;

    public GetUsers(final CommandInput input) {
        super(input);
        filterOnlineUsers = input.getCommand().equals("getOnlineUsers");
    }

    @Override
    protected CommandResult execute() throws InvalidOperation {
        Stream<User> users = UserDatabase.getInstance().getUsers().stream();
        if (filterOnlineUsers) {
            users = users.filter(User::isOnline);
        }

        List<String> commandResult = users.map(User::getUsername).toList();
        return resultBuilder.result(commandResult).build();
    }
}
