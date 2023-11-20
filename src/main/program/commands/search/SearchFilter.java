package main.program.commands.search;

import main.audio.Searchable;

import java.util.List;

/**
 * A filter used for searching.
 */
public final class SearchFilter {

    private final String filter;
    private final List<String> parameters;

    public SearchFilter(final String filter, final List<String> parameters) {
        this.filter = filter;
        this.parameters = parameters;
    }

    /**
     * Check if the item matches the filter's parameters.
     *
     * @param item the item to be checked.
     * @return true if the item matches all the filter's parameters.
     */
    public boolean matchItem(final Searchable item) {
        for (String parameter : parameters) {
            if (!item.matchFilter(filter, parameter)) {
                return false;
            }
        }

        return true;
    }
}
