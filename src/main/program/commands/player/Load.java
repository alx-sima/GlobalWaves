package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import java.util.ArrayList;
import main.audio.Searchable;
import main.program.Program;
import main.program.commands.Command;

public final class Load extends Command {

    public Load(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();

        Searchable selected = instance.getSelectedResult();
        instance.setSelectedResult(null);
        if (selected == null) {
            return new MessageResult(this, "Please select a source before attempting to load.");
        }

        instance.getPlayer().addQueue(selected.createQueue(), getTimestamp());
        return new MessageResult(this, "Playback loaded successfully.");
    }
}
