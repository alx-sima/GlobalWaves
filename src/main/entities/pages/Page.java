package main.entities.pages;

import main.entities.users.User;

public interface Page {

    /**
     * Prints the page as seen by the user.
     *
     * @param user the user that is viewing the page.
     * @return the page's contents as a string.
     */
    String printPageOfUser(User user);
}
