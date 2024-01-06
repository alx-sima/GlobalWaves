package main.program.notifications;

/**
 * An object that can subscribe to {@link Notifier}'s notifications.
 */
public interface Subscriber {

    /**
     * Be notified that a Notifier has an update.
     */
    void update(final Notification string);
}
