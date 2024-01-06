package fileio.input.commands;

import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.user.BuyMerch;
import main.program.commands.user.artist.RemoveAlbum;
import main.program.commands.user.artist.RemoveEvent;
import main.program.commands.user.host.RemoveAnnouncement;
import main.program.commands.user.host.RemovePodcast;

@Getter
@Setter
public class CommandInputWithName extends CommandInput {

    protected String name;

    /**
     * {@inheritDoc}
     */
    @Override
    public Command createCommand() {
        return switch (command) {
            case "buyMerch" -> new BuyMerch(this);
            case "removeAlbum" -> new RemoveAlbum(this);
            case "removeEvent" -> new RemoveEvent(this);
            case "removePodcast" -> new RemovePodcast(this);
            case "removeAnnouncement" -> new RemoveAnnouncement(this);
            default -> null;
        };
    }
}
