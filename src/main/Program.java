package main;

import main.audio.collections.Library;
import main.audio.collections.Podcast;

import java.util.List;

public final class Program {
    private static Program instance = null;

    private List<User> users;
    private List<Podcast> podcasts;
    private Library library;

    private Program() {
    }

    /**
     * Get the instance of the program
     * (creates it if it isn't running).
     *
     * @return the instance
     */
    public static Program getInstance() {
        if (instance == null) {
            instance = new Program();
        }

        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }

    public List<Podcast> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(final List<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(final Library library) {
        this.library = library;
    }
}
