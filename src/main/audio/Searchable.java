package main.audio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.audio.collections.RepeatMode;
import main.audio.files.AudioFile;

import java.util.List;

public interface Searchable {

    /**
     * Check if the entity (song, podcast, playlist) matches the filter and its parameter.
     *
     * @param filter    The search filter.
     * @param parameter The filter's parameter.
     * @return True if the entity matches the search.
     */
    boolean matchFilter(String filter, String parameter);

    /**
     * Get the files to be played when loading the search result.
     *
     * @return The list of audio files contained by the entity.
     */
    @JsonIgnore
    List<? extends AudioFile> getContents();

    /**
     * Get the name of the file.
     *
     * @return The name.
     */
    String getName();

    RepeatMode nextRepeatMode(RepeatMode mode);

    AudioFile getSongAt(int timePassed);

    void accept(SearchableVisitor visitor);
}
