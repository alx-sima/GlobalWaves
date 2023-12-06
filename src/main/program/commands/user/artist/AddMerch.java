package main.program.commands.user.artist;

import fileio.input.commands.AddMerchInput;
import fileio.output.CommandResult;
import main.program.commands.Command;

public final class AddMerch extends Command {

    private final String name;
    private final String description;
    private final int price;

    public AddMerch(final AddMerchInput input) {
        super(input);
        name = input.getName();
        description = input.getDescription();
        price = input.getPrice();
    }

    @Override
    public CommandResult execute() {
        // TODO
        return null;
    }
}
