package main.program.commands.search;

import fileio.input.commands.SelectInput;
import fileio.output.builders.ResultBuilder;
import java.util.List;
import lombok.Getter;
import main.entities.Searchable;
import main.entities.users.User;
import main.program.Searchbar;
import main.program.commands.user.OnlineUserCommand;

public final class Select extends OnlineUserCommand {

    @Getter
    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);
    private final int itemNumber;

    public Select(final SelectInput input) {
        super(input);
        itemNumber = input.getItemNumber();
    }

    @Override
    protected ResultBuilder execute(final User caller) {
        Searchbar searchbar = caller.getSearchbar();
        List<Searchable> searchResults = searchbar.getSearchResults();
        searchbar.clearSelection();

        if (searchResults == null) {
            return resultBuilder.withMessage("Please conduct a search before making a selection.");
        }

        if (itemNumber > searchResults.size()) {
            return resultBuilder.withMessage("The selected ID is too high.");
        }

        Searchable selected = searchbar.selectResult(itemNumber - 1);
        String selectionOutput = selected.selectResultBy(caller);
        return resultBuilder.withMessage("Successfully selected " + selectionOutput + ".");
    }
}
