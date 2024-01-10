package main.program.commands.search;

import fileio.input.commands.SelectInput;
import java.util.List;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.Searchable;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Searchbar;

public final class Select extends NoOutputCommand {

    private final int itemNumber;

    public Select(final SelectInput input) {
        super(input);
        itemNumber = input.getItemNumber();
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();

        Searchbar searchbar = caller.getSearchbar();
        List<Searchable> searchResults = searchbar.getSearchResults();
        searchbar.clearSelection();

        if (searchResults == null) {
            return "Please conduct a search before making a selection.";
        }

        if (itemNumber > searchResults.size()) {
            return "The selected ID is too high.";
        }

        Searchable selected = searchbar.selectResult(itemNumber - 1);
        String selectionOutput = selected.selectResultBy(caller);
        return "Successfully selected " + selectionOutput + ".";
    }
}
