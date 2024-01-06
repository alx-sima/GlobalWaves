package main.program.notifications;

import java.util.ArrayList;
import java.util.List;

/**
 * Object that sends notifications to {@link Subscriber}s.
 */
public final class Notifier {

    private final List<Subscriber> subscribers = new ArrayList<>();

    /**
     * Toggle the subscription to this item's notifications.
     *
     * @param subscriber the subscriber.
     * @return true if subscribed, false if unsubscribed.
     */
    public boolean subscribe(Subscriber subscriber) {
        boolean isSubscribed = subscribers.contains(subscriber);
        if (isSubscribed) {
            subscribers.remove(subscriber);
        } else {
            subscribers.add(subscriber);
        }

        return !isSubscribed;
    }

    /**
     * Signal the subscribers that this item has notifications.
     */
    public void updateSubscribers(final Notification notification) {
        subscribers.forEach(subscriber -> subscriber.update(notification));
    }
}
