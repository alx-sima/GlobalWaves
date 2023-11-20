package main.program.commands.search;

import main.audio.Searchable;

import java.util.List;

public final class SearchFilter  {

    private final String filter;
    private final List<String> parameters;

    public SearchFilter(final String filter, final List<String> parameters) {
        this.filter = filter;
        this.parameters = parameters;
    }

    public boolean matchItem(final Searchable item) {
        for (String parameter : parameters) {
            if (!item.matchFilter(filter, parameter)) {
                return false;
            }
        }

        return true;
    }
}
