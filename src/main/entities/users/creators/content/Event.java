package main.entities.users.creators.content;

import lombok.Getter;

/**
 * An event posted by an artist.
 */
public final class Event {

    @Getter
    private final String owner;
    @Getter
    private final String name;
    private final String description;
    private final String date;

    public Event(final String owner, final String name, final String description,
        final String date) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    @Override
    public String toString() {
        return name + " - " + date + ":\n\t" + description;
    }
}
