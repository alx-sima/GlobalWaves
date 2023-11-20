package fileio.input.commands;

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
public final class SearchInput extends CommandInput {

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private Map<String, List<String>> filters;
    private String type;

    public SearchInput() {
    }

    @Override
    public Command createCommand() {
        if (command.equals("search")) {
            return new Search(this);
        }
        return null;
    }

    /**
     * Create a list of filters for the search, based on the `filters` field.
     */
    public List<SearchFilter> createFilters() {
        return filters.entrySet().stream().map(entry -> {
            String filter = entry.getKey();
            List<String> parameters = entry.getValue();

            return new SearchFilter(filter, parameters);
        }).collect(Collectors.toList());
    }
}
