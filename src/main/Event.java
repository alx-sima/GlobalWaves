package main;

public final class Event {

    private final String owner;
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
}
