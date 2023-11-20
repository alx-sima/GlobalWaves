package main.program;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.audio.Searchable;

public class Searchbar {

    @Getter
    @Setter
    private List<Searchable> searchResults;
    private Searchable selectedResult;

    /**
     * Select a result from the search results.
     *
     * @param index the index of the result to select.
     * @return the selected result.
     */
    public Searchable SelectResult(final int index) {
        selectedResult = searchResults.get(index);
        return selectedResult;
    }

    /**
     * Use the selected search result, and clear the selection.
     *
     * @return the selected result.
     */
    public Searchable consumeSelectedResult() {
        Searchable result = selectedResult;
        selectedResult = null;
        return result;
    }
}
