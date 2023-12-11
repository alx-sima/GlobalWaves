package main.program;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.entities.Searchable;
import main.entities.audio.SearchableAudio;

/**
 * The search bar, used to search and select playlists, songs and podcasts.
 */
public class Searchbar {

    @Getter
    @Setter
    private List<Searchable> searchResults;
    private Searchable selectedResult;
    @Getter
    private SearchableAudio selectedAudioSource;

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
     * Select an audio source, while clearing other selections.
     *
     * @param source the selected source.
     */
    public void selectAudioSource(final SearchableAudio source) {
        selectedAudioSource = source;
        selectedResult = null;
    }

    /**
     * Clear the searchbar's selection.
     */
    public void clearSelection() {
        selectedAudioSource = null;
        selectedResult = null;
    }

    /**
     * Get the selected search result, then clear the selection.
     */
    public SearchableAudio consumeSelectedAudioSource() {
        SearchableAudio result = selectedAudioSource;
        clearSelection();
        return result;
    }
}
