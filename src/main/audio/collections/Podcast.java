package main.audio.collections;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import main.audio.Searchable;
import main.audio.files.AudioFile;
import main.audio.files.Episode;

import java.util.ArrayList;
import java.util.List;

public final class Podcast extends Playable implements Searchable {

    private final String name;
    private final String owner;
    private final List<Episode> episodes;
    private int currentEpisode = 0;

    public Podcast(final PodcastInput input) {
        name = input.getName();
        owner = input.getOwner();

        List<Episode> episodeList = new ArrayList<>();
        for (EpisodeInput episodeInput : input.getEpisodes()) {
            Episode episode = new Episode(episodeInput);
            episodeList.add(episode);
        }

        episodes = episodeList;
        currentlyPlaying = episodes.get(0);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<AudioFile> getContents() {
        return List.copyOf(episodes);
    }

    @Override
    public Playable createPlayable() {
        return this;
    }

    @Override
    public boolean matchFilter(final String filter, final String parameter) {
        return switch (filter) {
            case "name" -> name.startsWith(parameter);
            case "owner" -> owner.equals(parameter);
            default -> false;
        };
    }

    @Override
    public RepeatMode changeRepeatMode() {
        return switch (repeatMode) {
            case NO_REPEAT -> RepeatMode.REPEAT_ONCE;
            case REPEAT_ONCE -> RepeatMode.REPEAT_INFINITE;
            case REPEAT_INFINITE -> RepeatMode.NO_REPEAT;
            default -> null;
        };
    }

    @Override
    protected AudioFile getNext() {
        currentEpisode++;
        if (currentEpisode >= episodes.size()) {
            return null;
        }

        return episodes.get(currentEpisode);
    }

    @Override
    public void accept(PlayableVisitor visitor) {
        visitor.visit(this);
    }

}
