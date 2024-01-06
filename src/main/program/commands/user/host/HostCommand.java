package main.program.commands.user.host;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import java.util.stream.Stream;
import lombok.Getter;
import main.program.databases.UserDatabase;
import main.program.entities.users.creators.Host;
import main.program.commands.Command;

/**
 * A command that can be executed only by hosts.
 */
@Getter
public abstract class HostCommand extends Command {

    protected final MessageResult.Builder resultBuilder = new Builder(this);

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
            return resultBuilder.returnMessage("The username " + user + " doesn't exist.");
        }

        Host caller = getHost();
        if (caller == null) {
            return resultBuilder.returnMessage(user + " is not a host.");
        }

        return execute(caller);
    }

    /**
     * Executes the command as a host.
     *
     * @param host the host that called the command.
     * @return the result of the command.
     */
    protected abstract MessageResult execute(Host host);
}
