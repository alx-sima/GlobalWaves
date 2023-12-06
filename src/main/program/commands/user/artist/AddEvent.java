package main.program.commands.user.artist;

import fileio.input.commands.AddEventInput;
import fileio.output.CommandResult;
import main.program.commands.Command;

public final class AddEvent extends Command {

    private final String name;
    private final String description;
    private final String date;

    public AddEvent(final AddEventInput input) {
        super(input);
        this.name = input.getName();
        this.description = input.getDescription();
        this.date = input.getDate();
    }

    @Override
    public CommandResult execute() {
        // TODO
        return null;
    }
}
