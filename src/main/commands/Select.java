package main.commands;

import main.Program;
import main.audio.Searchable;

import java.util.List;

public final class Select extends Command {
    private final int itemNumber;

    public Select(final String command, final String user, final int timestamp,
                  final int itemNumber) {
        super(command, user, timestamp);
        this.itemNumber = itemNumber;
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
