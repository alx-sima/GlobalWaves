package main.entities.pages;

import main.entities.users.User;

public final class SwitchPageCommand {

    private Page prevPage;
    private Page nextPage;

    public SwitchPageCommand(final Page page) {
        nextPage = page;
    }

    /**
     * Execute this command.
     *
     * @param user the target user.
     */
    public void execute(final User user) {
        prevPage = user.getCurrentPage();
        user.setCurrentPage(nextPage);
    }

    /**
     * Undo this command.
     *
     * @param user the target user.
     */
    public void undo(final User user) {
        Page auxPage = nextPage;
        nextPage = prevPage;
        prevPage = auxPage;
        execute(user);
    }
}
