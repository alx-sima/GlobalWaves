package main.audio.collections;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import main.audio.Searchable;
import main.audio.files.Episode;

import java.util.ArrayList;
import java.util.List;

public final class Podcast implements Searchable {
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean matchFilter(String filter, String parameter) {
        return switch (filter) {
            case "name" -> name.startsWith(parameter);
            case "owner" -> owner.equals(parameter);
            default -> false;
        };
    }
}
