package main.program;

import lombok.Getter;
import main.entities.audio.SearchableAudio;
import main.entities.audio.collections.Playlist;
import main.entities.audio.files.Song;

@Getter
public final class Recommendations {

    private Song song;
    private Playlist playlist;

    /**
     * Set the `song` as recommended`.
     */
    public void setRecommendation(Song song) {
        this.song = song;
        playlist = null;
    }

    /**
     * Set the `playlist` as recommended.
     */
    public void setRecommendation(Playlist playlist) {
        this.playlist = playlist;
        song = null;
    }

    /**
     * Get the recommended audio.
     */
    public SearchableAudio getRecommendation() {
        if (song != null) {
            return song;
        }
        return playlist;
    }
}
