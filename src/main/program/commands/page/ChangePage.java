package main.program.commands.page;

import fileio.input.commands.ChangePageInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.pages.HomePage;
import main.entities.pages.LikedContentPage;
import main.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

public final class ChangePage extends OnlineUserCommand {

    @Getter
    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);
    private final String nextPage;

    public ChangePage(final ChangePageInput input) {
        super(input);
        nextPage = input.getNextPage();
    }

    @Override
    public ResultBuilder execute(final User caller) {
        switch (nextPage) {
            case "Home":
                caller.setCurrentPage(new HomePage(caller));
                break;
            case "LikedContent":
                caller.setCurrentPage(new LikedContentPage(caller));
                break;
            default:
                return resultBuilder.withMessage(
                    user + " is trying to access a non-existent page.");

        }
        return resultBuilder.withMessage(user + " accessed " + nextPage + " successfully.");
    }
}
