package main.program.commands.user.host;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.builders.ResultBuilder;
import java.util.stream.Stream;
import lombok.Getter;
import main.entities.users.UserDatabase;
import main.entities.users.host.Host;
import main.program.commands.Command;

/**
 * A command that can be executed only by hosts.
 */
@Getter
public abstract class HostCommand extends Command {

    protected final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    protected HostCommand(final CommandInput input) {
        super(input);
    }

    private Host getHost() {
        Stream<Host> hosts = UserDatabase.getInstance().getHosts().stream();
        return hosts.filter(host -> user.equals(host.getUsername())).findAny().orElse(null);
    }

    @Override
    public final CommandResult execute() {
        if (!UserDatabase.getInstance().existsUser(user)) {
            return resultBuilder.withMessage("The username " + user + " doesn't exist.").build();
        }

        Host caller = getHost();
        if (caller == null) {
            return resultBuilder.withMessage(user + " is not a host.").build();
        }

        return execute(caller).build();
    }

    /**
     * Executes the command as a host.
     *
     * @param host the host that called the command.
     * @return the result of the command.
     */
    protected abstract ResultBuilder execute(Host host);
}
