package main.entities.users.artist;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import main.entities.Searchable;
import main.entities.pages.ArtistPage;
import main.entities.users.User;

@Getter
public final class Artist extends User implements Searchable {

    private final List<Event> events = new ArrayList<>();
    private final List<Merch> merch = new ArrayList<>();

    public Artist(final String type, final String username, final int age, final String city) {
        super(type, username, age, city);
    }

    @Override
    public boolean matchFilter(final String filter, final String parameter) {
        if (filter.equals("name")) {
            return username.startsWith(parameter);
        }
        return false;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String selectResultBy(final User user) {
        user.setCurrentPage(new ArtistPage(this));
        return username + "'s page";
    }
}
