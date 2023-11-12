package main.audio.collections;

import main.User;
import main.audio.Searchable;
import main.audio.files.AudioFile;

import java.util.ArrayList;
import java.util.List;

public final class Playlist implements Searchable {
    private final String name;
    private final User user;
    private final boolean isPrivate = false;
    private final List<AudioFile> files = new ArrayList<>();

    public Playlist(final String name, final User user) {
        this.name = name;
        this.user = user;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean matchFilter(String filter, String parameter) {
        return switch (filter) {
            case "name" -> name.startsWith(parameter);
            case "owner" -> user.getUsername().equals(parameter);
            default -> false;
        };
    }

}
