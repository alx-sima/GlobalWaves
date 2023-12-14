package main.program.commands.user.artist;

import fileio.input.commands.AddMerchInput;
import fileio.output.builders.ResultBuilder;
import java.util.List;
import main.entities.users.artist.Artist;
import main.entities.users.artist.Merch;

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
    protected ResultBuilder execute(final Artist artist) {
        List<Merch> merchList = artist.getMerch();

        if (merchList.stream().anyMatch(merch -> merch.getName().equals(name))) {
            return getResultBuilder().withMessage(user + " has merchandise with the same name.");
        }

        if (price < 0) {
            return getResultBuilder().withMessage("Price for merchandise can not be negative.");
        }

        Merch merch = new Merch(user, name, description, price);
        merchList.add(merch);

        return getResultBuilder().withMessage(user + " has added new merchandise successfully.");
    }
}
