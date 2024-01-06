package main.program.commands.user.merch;

import fileio.input.commands.CommandInputWithName;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.entities.users.User;
import main.program.entities.users.creators.Creator;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class BuyMerch extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);
    private final String name;

    public BuyMerch(final CommandInputWithName input) {
        super(input);
        this.name = input.getName();
    }

    @Override
    protected MessageResult execute(final User caller) {
        Creator watchedCreator = caller.getCurrentPage().getPageOwner();
        try {
            if (!watchedCreator.buyMerch(caller, name)) {
                return resultBuilder.returnMessage("The merch " + name + " doesn't exist.");
            }

            return resultBuilder.returnMessage(user + " has added new merch successfully.");

        } catch (Exception e) {
            return resultBuilder.returnMessage("Cannot buy merch from this page.");
        }
    }
}
