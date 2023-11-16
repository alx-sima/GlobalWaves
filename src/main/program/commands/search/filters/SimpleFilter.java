package main.program.commands.search.filters;

import main.audio.Searchable;

public final class SimpleFilter extends Filter {

    private final String parameter;

    public SimpleFilter(final String filter, final String parameter) {
        super(filter);
        this.parameter = parameter;
    }

    @Override
    public boolean matchItem(final Searchable item) {
        return item.matchFilter(getFilter(), parameter);
    }
}
