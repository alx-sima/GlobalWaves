package main.program.commands.user.admin;

import fileio.input.commands.CommandInput;
import fileio.output.AlbumResult;
import fileio.output.CommandResult;
import fileio.output.PodcastResult;
import java.util.List;
import main.entities.audio.collections.Podcast;
import main.program.Library;
import main.program.Program;
import main.program.commands.Command;

public final class ShowPodcasts extends Command {

    public ShowPodcasts(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program program = Program.getInstance();
        Library library = program.getLibrary();
        List<Podcast> podcasts = library.getPodcasts().stream()
            .filter(podcast -> podcast.getOwner().equals(user)).toList();

        return new PodcastResult(this, podcasts);
    }
}
