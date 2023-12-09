package main.program.commands.user.artist;

import fileio.input.commands.AddMerchInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import java.util.List;
import main.entities.users.artist.Merch;
import main.program.Library;
import main.program.Program;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.IsArtistDependency;

public final class AddMerch extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final String name;
    private final String description;
    private final int price;

    public AddMerch(final AddMerchInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        name = input.getName();
        description = input.getDescription();
        price = input.getPrice();
    }

    @Override
    public CommandResult checkDependencies() {
        IsArtistDependency dependency = new IsArtistDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Program program = Program.getInstance();
        Library library = program.getLibrary();
        List<Merch> merchList = library.getMerch();

        if (merchList.stream().anyMatch(merch -> merch.getName().equals(name))) {
            return resultBuilder.withMessage(user + " has merchandise with the same name.");
        }

        if (price < 0) {
            return resultBuilder.withMessage("Price for merchandise can not be negative.");
        }

        Merch merch = new Merch(user, name, description, price);
        merchList.add(merch);

        return resultBuilder.withMessage(user + " has added new merchandise successfully.");
    }
}
