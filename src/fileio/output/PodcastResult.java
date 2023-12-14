package fileio.output;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.entities.audio.collections.Podcast;
import main.entities.audio.files.AudioFile;

@Getter
@Setter
public final class PodcastResult extends CommandResult {

    private List<PodcastOutput> result;

    public void setResult(final List<Podcast> result) {
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
