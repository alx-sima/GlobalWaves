package main.program.commands.search;

import fileio.input.commands.SelectInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import java.util.List;
import main.entities.Searchable;
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
        searchbar.clearSelectedResult();

        if (searchResults == null) {
            return resultBuilder.withMessage("Please conduct a search before making a selection.");
        }

        if (itemNumber > searchResults.size()) {
            return resultBuilder.withMessage("The selected ID is too high.");
        }

        Searchable selected = searchbar.selectResult(itemNumber - 1);
        String selectionOutput = selected.selectResultBy(getCaller());
        return resultBuilder.withMessage("Successfully selected " + selectionOutput + ".");
    }
}
