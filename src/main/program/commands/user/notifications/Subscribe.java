package main.program.commands.user.notifications;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.User;
import main.program.entities.users.creators.Creator;

public final class Subscribe extends NoOutputCommand {

    public Subscribe(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        Creator watchedCreator = caller.getCurrentPage().getPageOwner();
        if (watchedCreator == null) {
            return "To subscribe you need to be on the page of an artist or host.";
        }

        if (watchedCreator.getNotifier().subscribe(caller)) {
            return user + " subscribed to " + watchedCreator.getUsername() + " successfully.";
        }
        return user + " unsubscribed from " + watchedCreator.getUsername() + " successfully.";
    }
}
