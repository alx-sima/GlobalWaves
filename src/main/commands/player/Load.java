package main.commands.player;

import fileio.input.CommandInput;
import fileio.output.MessageResult;
import main.Program;
import main.audio.Player;
import main.audio.Searchable;
import main.audio.collections.Queue;
import main.commands.Command;
import fileio.output.CommandResult;

public final class Load extends Command {

    public Load(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();

        Searchable selected = instance.getSelectedResult();
        if (selected == null) {
            return new MessageResult(this, "Please select a source before attempting to load.");
        }

//        List<AudioFile> queue = selected.getContents();
//        if (queue.isEmpty()) {
//            return new Result(this, "You can't load an empty audio collection!");
//        }

        instance.setPlayer(new Player(new Queue(selected), getTimestamp()));
        return new MessageResult(this, "Playback loaded successfully.");
    }
}
