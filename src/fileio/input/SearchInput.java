package fileio.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.search.Search;
import main.program.commands.search.SearchFilter;

@Getter
@Setter
public class SearchInput extends CommandInput {

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private Map<String, List<String>> filters;

    public SearchInput() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Command createCommand() {
        if (command.equals("search")) {
            return new Search(this);
        }
        return null;
    }

    /**
     * Create a list of filters for the search, based on the `filters` field.
     *
     * @return the list of filters.
     */
    public List<SearchFilter> createFilters() {
        return filters.entrySet().stream().map(entry -> {
            String field = entry.getKey();
            List<String> values = entry.getValue();

            return new SearchFilter(field, values);
        }).collect(Collectors.toList());
    }
}
