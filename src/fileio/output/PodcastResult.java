package fileio.output;

import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Podcast;
import main.entities.audio.files.AudioFile;
import main.program.commands.Command;

@Getter
public final class PodcastResult extends MessageResult {

    private final List<PodcastOutput> result;

    private PodcastResult(final Builder builder) {
        super(builder);
        result = builder.result;
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

    public static final class Builder extends ResultBuilder {

        private List<PodcastOutput> result;

        public Builder(final Command cmd) {
            super(cmd);
        }

        /**
         * Set the result.
         */
        public Builder result(final List<Podcast> podcasts) {
            this.result = podcasts.stream().map(PodcastOutput::new).toList();
            return this;
        }

        @Override
        public MessageResult build() {
            return new PodcastResult(this);
        }
    }
}
