package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.entities.users.creators.Creator;
import main.entities.users.User;

@Getter
public final class Subscribe extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public Subscribe(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
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
