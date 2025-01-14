package main.program.entities.users.creators.content;

import lombok.Getter;

/**
 * An announcement posted by a host.
 */
public final class Announcement {

    @Getter
    private final String owner;
    @Getter
    private final String name;
    private final String description;

    public Announcement(final String owner, final String name, final String description) {
        this.owner = owner;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name + ":\n\t" + description + "\n";
    }
}
