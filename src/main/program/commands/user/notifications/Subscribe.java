package main.program.commands.user.notifications;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.User;
import main.program.entities.users.creators.Creator;

@Getter
public final class Subscribe extends Command {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public Subscribe(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        Creator watchedCreator = caller.getCurrentPage().getPageOwner();
        if (watchedCreator == null) {
            return resultBuilder.returnMessage(
                "To subscribe you need to be on the page of an artist or host.");
        }

        if (watchedCreator.getNotifier().subscribe(caller)) {
            return resultBuilder.returnMessage(
                user + " subscribed to " + watchedCreator.getUsername() + " successfully.");
        }
        return resultBuilder.returnMessage(
            user + " unsubscribed from " + watchedCreator.getUsername() + " successfully.");
    }
}
