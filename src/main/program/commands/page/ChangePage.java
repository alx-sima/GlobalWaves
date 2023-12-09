package main.program.commands.page;

import fileio.input.commands.ChangePageInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.entities.pages.HomePage;
import main.entities.pages.LikedContentPage;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class ChangePage extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final String nextPage;

    public ChangePage(final ChangePageInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        nextPage = input.getNextPage();
    }

    @Override
    public CommandResult checkDependencies() {
        OnlineUserDependency dependency = new OnlineUserDependency(this, resultBuilder);
        return dependency.checkDependencies();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        switch (nextPage) {
            case "Home":
                getCaller().setCurrentPage(new HomePage());
                break;
            case "LikedContent":
                getCaller().setCurrentPage(new LikedContentPage());
                break;
            default:
                return resultBuilder.withMessage(
                    user + " is trying to access a non-existent page.");

        }
        return resultBuilder.withMessage(user + " accessed " + nextPage + " successfully.");
    }
}
