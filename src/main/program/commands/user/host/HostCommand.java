package main.program.commands.user.host;

import fileio.input.commands.CommandInput;
import java.util.stream.Stream;
import lombok.Getter;
import main.program.commands.NoOutputCommand;
import main.program.databases.UserDatabase;
import main.program.entities.users.creators.Host;

/**
 * A command that can be executed only by hosts.
 */
@Getter
public abstract class HostCommand extends NoOutputCommand {

    protected HostCommand(final CommandInput input) {
        super(input);
    }

    private Host getHost() {
        Stream<Host> hosts = UserDatabase.getInstance().getHosts().stream();
        return hosts.filter(host -> user.equals(host.getUsername())).findAny().orElse(null);
    }

    @Override
    public final String executeNoOutput() {
        if (!UserDatabase.getInstance().existsUser(user)) {
            return "The username " + user + " doesn't exist.";
        }

        Host caller = getHost();
        if (caller == null) {
            return user + " is not a host.";
        }

        return returnExecutionMessage(caller);
    }

    /**
     * Executes the command as a host.
     *
     * @param host the host that called the command.
     * @return the result of the command.
     */
    protected abstract String returnExecutionMessage(Host host);
}
