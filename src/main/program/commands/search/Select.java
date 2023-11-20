package main.program.commands.search;

import fileio.input.CommandInput;
import fileio.output.MessageResult;
import main.program.Program;
import main.audio.Searchable;
import main.program.Searchbar;
import main.program.commands.Command;
import fileio.output.CommandResult;

import java.util.List;

public final class Select extends Command {

    private final int itemNumber;

    public Select(final CommandInput input) {
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

        Searchable selected = searchbar.SelectResult(itemNumber - 1);
        return new MessageResult(this, "Successfully selected " + selected.getName() + ".");
    }
}
