package main.program.commands.user.artist;

import fileio.input.commands.AddMerchInput;
import java.util.List;
import main.program.entities.users.creators.Artist;
import main.program.entities.users.creators.content.Merch;

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
    protected String returnExecutionMessage(final Artist artist) {
        List<Merch> merchList = artist.getMerch();

        if (merchList.stream().anyMatch(merch -> merch.getName().equals(name))) {
            return user + " has merchandise with the same name.";
        }

        if (price < 0) {
            return "Price for merchandise can not be negative.";
        }

        artist.addMerch(new Merch(user, name, description, price));

        return user + " has added new merchandise successfully.";
    }
}
