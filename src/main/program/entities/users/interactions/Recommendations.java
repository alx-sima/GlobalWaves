package main.program.entities.users.interactions;

import lombok.Getter;
import main.program.entities.audio.SearchableAudio;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.audio.files.Song;

@Getter
public final class Recommendations {

    private Song song;
    private Playlist playlist;

    /**
     * Set the song as recommended.
     */
    public boolean setRecommendation(final Song recommendation) {
        if (song != null && song.getName().equals(recommendation.getName())) {
            return false;
        }

        this.song = recommendation;
        return true;
    }

    /**
     * Set the playlist as recommended.
     */
    public void setRecommendation(final Playlist recommendation) {
        this.playlist = recommendation;
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
