package main.program.commands.search;

import fileio.input.commands.SelectInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import java.util.List;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.Searchable;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Searchbar;

public final class Select extends Command {

    @Getter
    private final MessageResult.Builder resultBuilder = new Builder(this);
    private final int itemNumber;

    public Select(final SelectInput input) {
        super(input);
        itemNumber = input.getItemNumber();
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();

        Searchbar searchbar = caller.getSearchbar();
        List<Searchable> searchResults = searchbar.getSearchResults();
        searchbar.clearSelection();

        if (searchResults == null) {
            return resultBuilder.returnMessage(
                "Please conduct a search before making a selection.");
        }

        if (itemNumber > searchResults.size()) {
            return resultBuilder.returnMessage("The selected ID is too high.");
        }

        Searchable selected = searchbar.selectResult(itemNumber - 1);
        String selectionOutput = selected.selectResultBy(caller);
        return resultBuilder.returnMessage("Successfully selected " + selectionOutput + ".");
    }
}
