package main.program.commands.page;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class NextPage extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public NextPage(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
        if (!caller.getPageHistory().redo()) {
            return resultBuilder.returnMessage("There are no pages left to go forward.");
        }

        return resultBuilder.returnMessage(
            "The user " + user + " has navigated successfully to the next page.");
    }
}
