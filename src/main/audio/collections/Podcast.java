package main.audio.collections;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import main.audio.files.Episode;

import java.util.ArrayList;
import java.util.List;

public class Podcast {
    private final String name;
    private final String owner;
    private final List<Episode> episodes;

    public Podcast(final PodcastInput input) {
        name = input.getName();
        owner = input.getOwner();

        List<Episode> episodeList = new ArrayList<>();
        for (EpisodeInput episodeInput : input.getEpisodes()) {
            Episode episode = new Episode(episodeInput);
            episodeList.add(episode);
        }

        this.episodes = episodeList;
    }
}
