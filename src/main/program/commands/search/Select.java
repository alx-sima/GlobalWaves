package main.program.commands.search;

import fileio.input.commands.CommandInput;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.Searchable;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Searchbar;

public final class Select extends NoOutputCommand {

    private final int itemNumber;

    public Select(final Input input) {
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

    @Getter
    @Setter
    public static final class Input extends CommandInput {

        private int itemNumber;

        @Override
        public Command createCommand() {
            return new Select(this);
        }
    }
}
