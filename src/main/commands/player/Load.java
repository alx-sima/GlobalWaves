package main.commands.player;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.Program;
import main.audio.Player;
import main.audio.Searchable;
import main.commands.Command;

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

        instance.setPlayer(new Player(selected.createPlayable(), getTimestamp()));
        return new MessageResult(this, "Playback loaded successfully.");
    }
}
