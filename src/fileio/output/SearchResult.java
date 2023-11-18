package fileio.output;

import java.util.List;
import lombok.Getter;
import main.program.commands.Command;

@Getter
public final class SearchResult extends MessageResult {

    private final List<String> results;

    public SearchResult(final Command command, final String message, final List<String> results) {
        super(command, message);
        this.results = results;
    }
}
