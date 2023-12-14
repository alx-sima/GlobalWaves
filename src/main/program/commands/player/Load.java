package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.audio.SearchableAudio;
import main.entities.users.User;
import main.program.Searchbar;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Load extends OnlineUserCommand {

    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    public Load(final CommandInput input) {
        super(input);
    }

    @Override
    public ResultBuilder execute(final User caller) {
        Searchbar searchbar = caller.getSearchbar();

        SearchableAudio selected = searchbar.consumeSelectedAudioSource();
        if (selected == null) {
            return resultBuilder.withMessage("Please select a source before attempting to load.");
        }

        // Clear the search results if the load was successful.
        searchbar.setSearchResults(null);
        caller.getPlayer().addQueue(selected, timestamp);
        return resultBuilder.withMessage("Playback loaded successfully.");
    }
}
