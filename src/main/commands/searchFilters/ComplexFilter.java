package main.commands.searchFilters;

import main.audio.Searchable;

import java.util.List;

public final class ComplexFilter extends Filter {
    private final List<String> parameters;

    public ComplexFilter(final String filter, final List<String> parameters) {
        super(filter);
        this.parameters = parameters;
    }

    @Override
    public boolean matchItem(final Searchable item) {
        for (String parameter : parameters) {
            if (!item.matchFilter(getFilter(), parameter)) {
                return false;
            }
        }

        return true;
    }
}
