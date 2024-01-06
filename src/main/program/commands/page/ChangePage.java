package main.program.commands.page;

import fileio.input.commands.ChangePageInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.entities.pages.HomePage;
import main.entities.pages.LikedContentPage;
import main.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

public final class ChangePage extends OnlineUserCommand {

    @Getter
    private final MessageResult.Builder resultBuilder = new Builder(this);
    private final String nextPage;

    public ChangePage(final ChangePageInput input) {
        super(input);
        nextPage = input.getNextPage();
    }

    @Override
    public MessageResult execute(final User caller) {
        switch (nextPage) {
            case "Home":
                caller.getPageHistory().changePage(new HomePage(caller));
                break;
            case "LikedContent":
                caller.getPageHistory().changePage(new LikedContentPage(caller));
                break;
            default:
                return resultBuilder.returnMessage(
                    user + " is trying to access a non-existent page.");

        }
        return resultBuilder.returnMessage(user + " accessed " + nextPage + " successfully.");
    }
}
