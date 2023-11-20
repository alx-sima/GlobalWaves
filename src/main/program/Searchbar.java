package main.program;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.audio.Searchable;

/**
 * The search bar, used to search and select playlists, songs and podcasts.
 */
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
    public Searchable selectResult(final int index) {
        selectedResult = searchResults.get(index);
        return selectedResult;
    }

    /**
     * Get the selected search result, then clear the selection.
     */
    public Searchable consumeSelectedResult() {
        Searchable result = selectedResult;
        selectedResult = null;
        return result;
    }
}
