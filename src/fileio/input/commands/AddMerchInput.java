package fileio.input.commands;

import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.user.artist.AddMerch;

@Getter
@Setter
public final class AddMerchInput extends CommandInputWithName {

    private String description;
    private int price;

    @Override
    public Command createCommand() {
        return new AddMerch(this);
    }
}
