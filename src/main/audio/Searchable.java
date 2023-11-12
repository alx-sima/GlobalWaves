package main.audio;

public interface Searchable {
    /**
     * Check if the entity (song, podcast, playlist) matches the filter and its parameter.
     *
     * @param filter    The search filter.
     * @param parameter The filter's parameter.
     * @return True if the entity matches the search.
     */
    boolean matchFilter(String filter, String parameter);

    /**
     * Get the name of the file.
     *
     * @return The name.
     */
    String getName();
}
