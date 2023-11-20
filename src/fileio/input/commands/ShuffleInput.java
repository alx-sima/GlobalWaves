package fileio.input.commands;

import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.player.Shuffle;

@Getter
@Setter
public final class ShuffleInput extends CommandInput {

    private int seed;

    public ShuffleInput() {
    }

    @Override
    public Command createCommand() {
        if (command.equals("shuffle")) {
            return new Shuffle(this);
        }

        return null;
    }
}
