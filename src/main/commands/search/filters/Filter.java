package main.commands.search.filters;

import main.audio.Searchable;

public abstract class Filter {

    private final String filter;

    protected Filter(final String filter) {
        this.filter = filter;
    }

    /**
     * Get the type of the filter.
     *
     * @return The search criterion.
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Checks if the `item` matches this filter.
     *
     * @param item The item that is to be searched.
     * @return True if the item matches the search.
     */
    public abstract boolean matchItem(Searchable item);
}

