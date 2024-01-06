package fileio.input.commands;

import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.playlist.AdBreak;

@Getter
@Setter
public final class AdBreakInput extends CommandInput {
    private double price;

    @Override
    public Command createCommand() {
        return new AdBreak(this);
    }
}
