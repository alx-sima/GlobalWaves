package fileio.input.commands;

import fileio.input.EpisodeInput;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.user.host.AddPodcast;

@Getter
@Setter
public final class AddPodcastInput extends CommandInputWithName {

    private List<EpisodeInput> episodes;

    @Override
    public Command createCommand() {
        return new AddPodcast(this);
    }
}
