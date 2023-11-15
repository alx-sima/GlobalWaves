package main.commands.player;

import fileio.input.CommandInput;
import main.Program;
import main.audio.Player;
import main.audio.Searchable;
import main.audio.collections.Queue;
import main.audio.files.AudioFile;
import main.commands.Command;
import main.commands.Result;

import java.util.List;

public final class Load extends Command {

    public Load(final CommandInput input) {
        super(input);
    }

    @Override
    public Result execute() {
        Program instance = Program.getInstance();

        Searchable selected = instance.getSelectedResult();
        if (selected == null) {
            return new Result(this, "Please select a source before attempting to load.");
        }

//        List<AudioFile> queue = selected.getContents();
//        if (queue.isEmpty()) {
//            return new Result(this, "You can't load an empty audio collection!");
//        }

        instance.setPlayer(new Player(new Queue(selected), getTimestamp()));
        return new Result(this, "Playback loaded successfully.");
    }
}
