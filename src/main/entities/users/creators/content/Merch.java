package main.entities.users.creators.content;

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
    @Getter
    private double totalEarned = 0.0d;

    public Merch(final String owner, final String name, final String description, final int price) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Record one sale of the merch.
     */
    public void buyMerch() {
        totalEarned += price;
    }

    @Override
    public String toString() {
        return name + " - " + price + ":\n\t" + description;
    }
}
