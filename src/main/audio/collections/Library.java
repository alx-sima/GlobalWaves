package main.audio.collections;

import fileio.input.SongInput;
import main.audio.files.Song;

import java.util.ArrayList;
import java.util.List;

public final class Library {

    private final List<Song> songs;

    public Library(final List<SongInput> input) {
        List<Song> songList = new ArrayList<>();

        for (SongInput songInput : input) {
            Song song = new Song(songInput);
            songList.add(song);
        }

        this.songs = songList;
    }

    public List<Song> getSongs() {
        return songs;
    }
}
