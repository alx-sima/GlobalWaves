package main.entities.users.artist;

import lombok.Getter;

/**
 * Merch sold by an artist.
 */
public final class Merch {

    @Getter
    private final String owner;
    @Getter
    private final String name;
    private final String description;
    private final int price;

    public Merch(final String owner, final String name, final String description, final int price) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " - " + price + ":\n\t" + description;
    }
}
