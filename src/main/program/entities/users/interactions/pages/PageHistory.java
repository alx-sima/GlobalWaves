package main.program.entities.users.interactions.pages;

import java.util.Deque;
import java.util.LinkedList;
import main.program.entities.users.User;

public class PageHistory {

    private final Deque<SwitchPageCommand> commandHistory = new LinkedList<>();
    private final Deque<SwitchPageCommand> undoHistory = new LinkedList<>();
    private final User user;

    public PageHistory(final User user) {
        this.user = user;
    }

    /**
     * Switch to a different page, and record it to history.
     *
     * @param nextPage the next page.
     */
    public void changePage(final Page nextPage) {
        SwitchPageCommand command = new SwitchPageCommand(nextPage);
        command.execute(user);

        commandHistory.push(command);
        undoHistory.clear();
    }

    /**
     * Transfer a command between two stacks and undo it.
     *
     * @return true if there exists such a command.
     */
    private boolean transferBetweenStacks(final Deque<SwitchPageCommand> destination,
        final Deque<SwitchPageCommand> source) {
        if (source.isEmpty()) {
            return false;
        }

        SwitchPageCommand command = source.pop();
        destination.push(command);
        command.undo(user);
        return true;
    }

    /**
     * Switch to a previous page.
     *
     * @return false if there is no previous page.
     */
    public boolean undo() {
        return transferBetweenStacks(undoHistory, commandHistory);
    }

    /**
     * Redo a previous undo.
     *
     * @return false if the last page operation wasn't an undo.
     */
    public boolean redo() {
        return transferBetweenStacks(commandHistory, undoHistory);
    }

}
