package main.program.commands.search;

import fileio.input.commands.SelectInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import java.util.List;
import main.audio.Searchable;
import main.program.Program;
import main.program.Searchbar;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class Select extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final int itemNumber;

    public Select(final SelectInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        itemNumber = input.getItemNumber();
    }

    @Override
    public CommandResult checkDependencies() {
        OnlineUserDependency dependency = new OnlineUserDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Program program = Program.getInstance();
        Searchbar searchbar = program.getSearchbar();
        List<Searchable> searchResults = searchbar.getSearchResults();

        // Clear the selection. Without this, the tests fail only on VM-checker.
        // I have tested this on different machines and different Java versions.
        searchbar.clearSelectedResult();

        if (searchResults == null) {
            return resultBuilder.withMessage("Please conduct a search before making a selection.");
        }

        if (itemNumber > searchResults.size()) {
            return resultBuilder.withMessage("The selected ID is too high.");
        }

        Searchable selected = searchbar.selectResult(itemNumber - 1);
        return resultBuilder.withMessage("Successfully selected " + selected.getName() + ".");
    }
}
