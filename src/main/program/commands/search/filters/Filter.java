package main.program.commands.search.filters;

import lombok.Getter;
import main.audio.Searchable;

@Getter
public abstract class Filter {

    private final String filter;

    protected Filter(final String filter) {
        this.filter = filter;
    }

    /**
     * Checks if the `item` matches this filter.
     *
     * @param item The item that is to be searched.
     * @return True if the item matches the search.
     */
    public abstract boolean matchItem(Searchable item);
}

