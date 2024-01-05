package fileio.output;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import main.program.commands.Command;

/**
 * A {@link CommandResult} that contains a message and a list of results.
 */
@Getter
public final class SearchResult extends MessageResult {

    private final List<String> results;

    private SearchResult(final Builder builder) {
        super(builder);
        results = builder.results;
    }

    public static final class Builder extends ResultBuilder {

        private List<String> results = new ArrayList<>();

        public Builder(final Command cmd) {
            super(cmd);
        }

        /**
         * Set the search results.
         */
        public Builder results(final List<String> searchResults) {
            results = searchResults;
            return this;
        }

        @Override
        public MessageResult build() {
            return new SearchResult(this);
        }
    }
}
