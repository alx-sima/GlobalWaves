package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.Searchable;
import main.program.Program;
import main.program.Searchbar;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class Load extends OnlineCommand {

    public Load(final CommandInput input) {
        super(input);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        Program program = Program.getInstance();
        Searchbar searchbar = program.getSearchbar();

        Searchable selected = searchbar.consumeSelectedResult();
        if (selected == null) {
            return new MessageResult(this, "Please select a source before attempting to load.");
        }

        // Clear the search results if the load was successful.
        searchbar.setSearchResults(null);

        User caller = getCaller();
        caller.getPlayer().addQueue(selected.createQueue(), timestamp);
        return new MessageResult(this, "Playback loaded successfully.");
    }
}
