package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.entities.audio.SearchableAudio;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Searchbar;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Load extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public Load(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
        Searchbar searchbar = caller.getSearchbar();

        SearchableAudio selected = searchbar.consumeSelectedAudioSource();
        if (selected == null) {
            return resultBuilder.returnMessage("Please select a source before attempting to load.");
        }

        // Clear the search results if the load was successful.
        searchbar.setSearchResults(null);
        caller.getPlayer().addQueue(caller, selected, timestamp);
        return resultBuilder.returnMessage("Playback loaded successfully.");
    }
}
