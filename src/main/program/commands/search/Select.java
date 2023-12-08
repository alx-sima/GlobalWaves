package main.program.commands.search;

import fileio.input.commands.SelectInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
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
    protected MessageResultBuilder createResultBuilder() {
        return new MessageResultBuilder(this);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        MessageResultBuilder resultBuilder = createResultBuilder();

        Program program = Program.getInstance();
        Searchbar searchbar = program.getSearchbar();
        List<Searchable> searchResults = searchbar.getSearchResults();

        // Clear the selection. Without this, the tests fail only on VM-checker.
        // I have tested this on different machines and different Java versions.
        searchbar.clearSelectedResult();

        if (searchResults == null) {
            resultBuilder.withMessage("Please conduct a search before making a selection.");
            return resultBuilder.build();
        }

        if (itemNumber > searchResults.size()) {
            resultBuilder.withMessage("The selected ID is too high.");
            return resultBuilder.build();
        }

        Searchable selected = searchbar.selectResult(itemNumber - 1);
        resultBuilder.withMessage("Successfully selected " + selected.getName() + ".");
        return resultBuilder.build();
    }
}
