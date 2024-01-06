package main.program.commands.user.artist;

import fileio.input.commands.AddMerchInput;
import fileio.output.MessageResult;
import java.util.List;
import main.entities.users.creators.Artist;
import main.entities.users.creators.content.Merch;

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
    protected MessageResult execute(final Artist artist) {
        List<Merch> merchList = artist.getMerch();

        if (merchList.stream().anyMatch(merch -> merch.getName().equals(name))) {
            return getResultBuilder().returnMessage(user + " has merchandise with the same name.");
        }

        if (price < 0) {
            return getResultBuilder().returnMessage("Price for merchandise can not be negative.");
        }

        artist.addMerch(new Merch(user, name, description, price));

        return getResultBuilder().returnMessage(user + " has added new merchandise successfully.");
    }
}
