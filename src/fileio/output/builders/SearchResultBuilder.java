package fileio.output.builders;

import fileio.output.CommandResult;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;

/**
 * A builder for {@link SearchResult}.
 */
@Getter
public final class SearchResultBuilder extends ResultBuilder {

    private final SearchResult result = new SearchResult();

    /**
     * Sets the search result.
     *
     * @param searchResult list of the names of the results.
     */
    public SearchResultBuilder withResult(final List<String> searchResult) {
        result.setResults(searchResult);
        return this;
    }

    @Override
    public SearchResultBuilder withCommand(final Command command) {
        result.setCommand(command.getCommand());
        result.setUser(command.getUser());
        result.setTimestamp(command.getTimestamp());
        return this;
    }

    @Override
    public SearchResultBuilder withMessage(final String message) {
        result.setMessage(message);
        return this;
    }

    @Override
    public CommandResult build() {
        return result;
    }

    /**
     * A {@link CommandResult} that contains a message and a list of results.
     */
    @Getter
    @Setter
    private static final class SearchResult extends MessageResult {

        private List<String> results = new ArrayList<>();
    }
}