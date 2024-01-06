package fileio.output.wrapped;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;
import lombok.Getter;
import main.program.entities.audio.collections.Podcast;
import main.program.entities.audio.files.Episode;
import main.program.entities.users.User;
import main.program.entities.users.interactions.wrapped.CreatorWrapped;
import main.program.entities.users.creators.Host;
import main.program.databases.Library;

@Getter
public final class HostWrapped implements WrappedOutput {

    private final Map<Pair, Integer> topEpisodes;
    private final int listeners;

    public HostWrapped(final Host host) {
        List<Podcast> podcasts = Library.getInstance().getPodcasts();
        Stream<Podcast> ownedPodcasts = podcasts.stream()
            .filter(podcast -> podcast.getOwner().equals(host.getName()));

        Iterator<Episode> episodes = ownedPodcasts.flatMap(
            podcast -> podcast.getEpisodes().stream()).iterator();

        Map<String, Integer> episodeMap = new HashMap<>();
        Set<String> fanSet = new HashSet<>();

        while (episodes.hasNext()) {
            Episode episode = episodes.next();

            int totalListens = episode.getListeners().values().stream().reduce(0, Integer::sum);
            if (totalListens == 0) {
                continue;
            }

            CreatorWrapped.add(episodeMap, episode.getName(), totalListens);
            for (Entry<User, Integer> entry : episode.getListeners().entrySet()) {
                fanSet.add(entry.getKey().getUsername());
            }
        }

        topEpisodes = WrappedOutput.getTop(episodeMap);
        listeners = fanSet.size();
    }

    @Override
    public String returnMessage() {
        if (topEpisodes.isEmpty() && listeners == 0) {
            return "No data to show for host %s.";
        }

        return null;
    }
}
