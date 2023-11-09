package main;

import fileio.input.UserInput;
import main.audio.collections.Playlist;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private final int age;
    private final String city;
    private List<Playlist> playlists = new ArrayList<>();

    public User(final UserInput input) {
        username = input.getUsername();
        age = input.getAge();
        city = input.getCity();
    }
}
