package main.entities.pages;

import main.entities.users.User;

public abstract class Page {

    protected final User user;

    protected Page(final User user) {
        this.user = user;
    }

    /**
     * Prints the page as seen by the user.
     *
     * @return the page's contents as a string.
     */
    public abstract String printPage();

    /**
     * Get the user (artist/host) whose page this is.
     *
     * @return the reference to the user or null (default) if the page doesn't show another user.
     */
    public User getPageOwner() {
        return null;
    }
}
