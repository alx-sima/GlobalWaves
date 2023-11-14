package main.commands.search;

import fileio.input.CommandInput;
import main.Program;
import main.audio.Searchable;
import main.commands.Command;
import main.commands.Result;

import java.util.List;

public final class Select extends Command {
    private final int itemNumber;

    public Select (final CommandInput input) {
        super(input);
        itemNumber = input.getItemNumber();
    }

    @Override
    public Result execute() {
        Program instance = Program.getInstance();
        List<Searchable> searchResults = instance.getSearchResults();

        if (searchResults.isEmpty()) {
            return new Result(this, "Please conduct a search before making a selection.");
        }

        if (itemNumber > searchResults.size()) {
            return new Result(this, "The selected ID is too high.");
        }

        Searchable selected = searchResults.get(itemNumber - 1);
        instance.setSelectedResult(selected);
        return new Result(this, "Successfully selected " + selected.getName() + ".");
    }
}
