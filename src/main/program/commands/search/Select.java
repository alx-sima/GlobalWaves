package main.program.commands.search;

import fileio.input.commands.SelectInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import java.util.List;
import main.audio.Searchable;
import main.program.Program;
import main.program.Searchbar;
import main.program.commands.Command;

public final class Select extends Command {

    private final int itemNumber;

    public Select(final SelectInput input) {
        super(input);
        itemNumber = input.getItemNumber();
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        Searchbar searchbar = instance.getSearchbar();
        List<Searchable> searchResults = searchbar.getSearchResults();

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
