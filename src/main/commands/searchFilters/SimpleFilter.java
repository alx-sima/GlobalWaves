package main.commands.searchFilters;

import main.audio.Searchable;

public final class SimpleFilter extends Filter {
    private final String parameter;

    public SimpleFilter(String filter, String parameter) {
        super(filter);
        this.parameter = parameter;
    }

    @Override
    public boolean matchItem(Searchable item) {
        return item.matchFilter(getFilter(), parameter);
    }
}
