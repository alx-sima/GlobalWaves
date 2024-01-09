package main.program.commands.player;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.SearchableAudio;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Searchbar;

public final class Load extends NoOutputCommand {

    public Load(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        Searchbar searchbar = caller.getSearchbar();

        SearchableAudio selected = searchbar.consumeSelectedAudioSource();
        if (selected == null) {
            return "Please select a source before attempting to load.";
        }

        // Clear the search results if the load was successful.
        searchbar.setSearchResults(null);
        caller.getPlayer().addQueue(caller, selected, timestamp);
        return "Playback loaded successfully.";
    }
}
