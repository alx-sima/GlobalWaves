package main.program.commands.search;

import fileio.input.commands.SelectInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import java.util.List;
import main.audio.Searchable;
import main.program.Program;
import main.program.Searchbar;
import main.program.commands.OnlineCommand;

public final class Select extends OnlineCommand {

    private final int itemNumber;

    public Select(final SelectInput input) {
        super(input);
        itemNumber = input.getItemNumber();
    }

    @Override
    protected CommandResult executeWhenOnline() {
        Program program = Program.getInstance();
        Searchbar searchbar = program.getSearchbar();
        List<Searchable> searchResults = searchbar.getSearchResults();

        // Clear the selection. Without this, the tests fail only on VM-checker.
        // I have tested this on different machines and different Java versions.
        searchbar.clearSelectedResult();

        if (searchResults == null) {
            return new MessageResult(this, "Please conduct a search before making a selection.");
        }

        if (itemNumber > searchResults.size()) {
            return new MessageResult(this, "The selected ID is too high.");
        }

        Searchable selected = searchbar.selectResult(itemNumber - 1);
        return new MessageResult(this, "Successfully selected " + selected.getName() + ".");
    }
}
