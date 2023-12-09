package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.entities.audio.SearchableAudio;
import main.entities.users.User;
import main.program.Searchbar;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class Load extends DependentCommand {

    private final ResultBuilder resultBuilder;
    public Load(final CommandInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
    }

    @Override
    public CommandResult checkDependencies() {
        OnlineUserDependency dependency = new OnlineUserDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Searchbar searchbar = getCaller().getSearchbar();

        SearchableAudio selected = searchbar.consumeSelectedResult();
        if (selected == null) {
            return resultBuilder.withMessage("Please select a source before attempting to load.");
        }

        // Clear the search results if the load was successful.
        searchbar.setSearchResults(null);

        User caller = getCaller();
        caller.getPlayer().addQueue(selected, timestamp);

        return resultBuilder.withMessage("Playback loaded successfully.");
    }
}
