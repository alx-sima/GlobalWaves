package main.program.entities.users.interactions.notifications;

import lombok.Getter;

/**
 * A notification sent by a {@link Notifier}.
 */
@Getter
public final class Notification {

    private final String name;
    private final String description;

    public Notification(final String name, final String description) {
        this.name = name;
        this.description = description;
    }
}
