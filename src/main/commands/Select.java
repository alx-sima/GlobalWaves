package main.commands;

import main.Program;
import main.audio.Searchable;

import java.util.List;

public class Select extends Command {
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
            return new Result("select", getUser(), getTimestamp(), "Please conduct a search " +
                    "before making a selection.", null);
        }

        if (itemNumber >= searchResults.size()) {
            return new Result("select", getUser(), getTimestamp(), "The selected ID is too high.",
                    null);
        }

        Searchable selected = searchResults.get(itemNumber - 1);
        return new Result("select", getUser(), getTimestamp(),
                "Successfully selected " + selected.getName() + ".", null);
    }
}
