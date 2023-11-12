package main.commands.searchFilters;

import main.audio.Searchable;

public abstract class Filter {
    private final String filter;

    protected Filter(String filter) {
        this.filter = filter;
    }

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

