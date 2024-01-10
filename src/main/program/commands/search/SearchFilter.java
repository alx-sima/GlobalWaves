package main.program.commands.search;

import java.util.List;
import main.program.entities.Searchable;

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
     * Check if the string starts with the prefix, ignoring case.
     *
     * @param string the string that shall start with the prefix.
     * @param prefix the prefix to be checked.
     * @return true when, if ignoring the case, the string starts with the prefix.
     * @implNote Because I was experiencing huge amounts of lag, I profiled the app. This method of
     * checking gives an insane performance boost (1517 ms vs 6122 ms just for the search) compared
     * to the previous approach (`string.toLowerCase().startsWith(prefix.toLowerCase())`).
     */
    public static boolean isPrefixCaseInsensitive(final String string, final String prefix) {
        return string.regionMatches(true, 0, prefix, 0, prefix.length());
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
