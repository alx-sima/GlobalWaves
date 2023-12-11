package fileio.output;

import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Podcast;
import main.entities.audio.files.AudioFile;
import main.program.commands.Command;

@Getter
public class PodcastResult extends CommandResult {

    private final List<PodcastOutput> result;

    public PodcastResult(final Command command, final List<Podcast> result) {
        super(command);
        this.result = result.stream().map(PodcastOutput::new).toList();
    }

    @Getter
    private static final class PodcastOutput {

        private final String name;
        private final List<String> episodes;

        PodcastOutput(final Podcast podcast) {
            this.name = podcast.getName();
            this.episodes = podcast.getEpisodes().stream().map(AudioFile::getName).toList();
        }
    }
}
