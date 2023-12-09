package main.entities.pages;

import java.util.List;
import java.util.stream.Stream;
import main.entities.audio.collections.Playlist;
import main.entities.audio.files.AudioFile;
import main.entities.audio.files.Song;
import main.entities.users.User;

public class LikedContentPage implements Page {

    protected List<String> getLikedSongs(User user) {
        Stream<Song> songs = user.getLikedSongs().stream();
        return songs.map(AudioFile::getName).toList();
    }

    protected List<String> getFollowedPlaylists(User user) {
        Stream<Playlist> playlists = user.getPlaylists().stream();
        return playlists.map(Playlist::getName).toList();
    }

    @Override
    public String printPageOfUser(User user) {
        return "Liked songs:\n\t" + getLikedSongs(user) + "\n\nFollowed playlists:\n\t"
            + getFollowedPlaylists(user);
    }

}
