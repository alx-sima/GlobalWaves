package main.program.commands.user.merch;

import fileio.input.commands.CommandWithNameInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.User;
import main.program.entities.users.creators.Creator;

public final class BuyMerch extends NoOutputCommand {

    private final String name;

    public BuyMerch(final CommandWithNameInput input) {
        super(input);
        this.name = input.getName();
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        Creator watchedCreator = caller.getCurrentPage().getPageOwner();
        try {
            if (!watchedCreator.buyMerch(caller, name)) {
                return "The merch " + name + " doesn't exist.";
            }

            return user + " has added new merch successfully.";

        } catch (Exception e) {
            return "Cannot buy merch from this page.";
        }
    }
}
