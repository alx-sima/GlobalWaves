package main.commands;

import main.Program;
import main.audio.Player;
import main.audio.Searchable;
import main.audio.files.AudioFile;

import java.util.List;

public final class Load extends Command {
    public Load(final String command, final String user, final int timestamp) {
        super(command, user, timestamp);
    }

    @Override
    public Result execute() {
        Program instance = Program.getInstance();
        Player player = instance.getPlayer();

        Searchable selected = instance.getSelectedResult();
        if (selected == null) {
            return new Result(this, "Please select a source before attempting to load.");
        }

        List<AudioFile> queue = selected.getContents();
        if (queue.isEmpty()) {
            return new Result(this, "You can't load an empty audio collection!");
        }

        player.setQueue(queue);

        return new Result(this, "Playback loaded successfully.");
    }
}
