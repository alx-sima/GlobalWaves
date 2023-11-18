package fileio.output;

import java.util.List;
import lombok.Getter;
import main.program.commands.Command;

@Getter
public final class ShowPreferredSongsResult extends CommandResult {

    private final List<String> result;

    public ShowPreferredSongsResult(final Command command, final List<String> result) {
        super(command);
        this.result = result;
    }
}
