package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.Searchable;
import main.program.Program;
import main.program.Searchbar;
import main.program.User;
import main.program.commands.Command;

public final class Load extends Command {

    public Load(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        Searchbar searchbar = instance.getSearchbar();

        Searchable selected = searchbar.consumeSelectedResult();
        if (selected == null) {
            return new MessageResult(this, "Please select a source before attempting to load.");
        }

        // Clear the search results if the load was successful.
        searchbar.setSearchResults(null);

        User callee = getCallee();
        callee.getPlayer().addQueue(selected.createQueue(), timestamp);
        return new MessageResult(this, "Playback loaded successfully.");
    }
}
