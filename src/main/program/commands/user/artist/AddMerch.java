package main.program.commands.user.artist;

import fileio.input.commands.AddMerchInput;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import java.util.Map;
import main.Merch;
import main.audio.collections.Library;
import main.program.Program;

public final class AddMerch extends ArtistCommand {

    private final String name;
    private final String description;
    private final int price;

    public AddMerch(final AddMerchInput input) {
        super(input);
        name = input.getName();
        description = input.getDescription();
        price = input.getPrice();
    }

    @Override
    protected ResultBuilder executeAsArtist() {
        MessageResultBuilder resultBuilder = new MessageResultBuilder(this);

        Program program = Program.getInstance();
        Library library = program.getLibrary();
        Map<String, Merch> merchMap = library.getMerch();

        if (merchMap.containsKey(name)) {
            return resultBuilder.withMessage(user + " has merchandise with the same name.");
        }

        if (price < 0) {
            return resultBuilder.withMessage("Price for merchandise can not be negative.");
        }

        Merch merch = new Merch(name, description, price);
        merchMap.put(name, merch);

        return resultBuilder.withMessage(user + " has added new merchandise successfully.");
    }
}
