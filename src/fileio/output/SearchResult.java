package fileio.output;

import java.util.List;
import main.program.commands.Command;

public final class SearchResult extends MessageResult {

    private final List<String> results;

    public SearchResult(final Command command, final String message, final List<String> results) {
        super(command, message);
        this.results = results;
    }

    public List<String> getResults() {
        return results;
    }
}
