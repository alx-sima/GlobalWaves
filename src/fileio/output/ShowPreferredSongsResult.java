package fileio.output;

import java.util.List;
import main.program.commands.Command;

public final class ShowPreferredSongsResult extends CommandResult {

    private final List<String> result;

    public ShowPreferredSongsResult(final Command command, final List<String> result) {
        super(command);
        this.result = result;
    }

    public List<String> getResult() {
        return result;
    }
}
