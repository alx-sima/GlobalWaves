package fileio.input;

import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.player.Backward;
import main.program.commands.player.Forward;
import main.program.commands.player.Next;
import main.program.commands.player.Prev;
import main.program.commands.player.Repeat;
import main.program.commands.playlist.AddRemoveInPlaylist;
import main.program.commands.playlist.CreatePlaylist;
import main.program.commands.player.Load;
import main.program.commands.player.PlayPause;
import main.program.commands.player.Shuffle;
import main.program.commands.player.Status;
import main.program.commands.playlist.Follow;
import main.program.commands.playlist.Like;
import main.program.commands.playlist.ShowPlaylists;
import main.program.commands.playlist.ShowPreferredSongs;
import main.program.commands.playlist.SwitchVisibility;
import main.program.commands.search.Search;
import main.program.commands.search.Select;

@Getter
@Setter
public final class CommandInput {

    private String command;
    private String username;
    private int timestamp;
    private String type;
    private FiltersInput filters;

    private int itemNumber;
    private int seed;
    private int playlistId;
    private String playlistName;

    public CommandInput() {
    }

    /**
     * Create a command, based on the `type` field.
     *
     * @return The associated command.
     */
    public Command createCommand() {
        return switch (command) {
            case "backward" -> new Backward(this);
            case "forward" -> new Forward(this);
            case "load" -> new Load(this);
            case "next" -> new Next(this);
            case "playPause" -> new PlayPause(this);
            case "prev" -> new Prev(this);
            case "repeat" -> new Repeat(this);
            case "shuffle" -> new Shuffle(this);
            case "status" -> new Status(this);
            case "addRemoveInPlaylist" -> new AddRemoveInPlaylist(this);
            case "createPlaylist" -> new CreatePlaylist(this);
            case "follow" -> new Follow(this);
            case "like" -> new Like(this);
            case "showPlaylists" -> new ShowPlaylists(this);
            case "showPreferredSongs" -> new ShowPreferredSongs(this);
            case "switchVisibility" -> new SwitchVisibility(this);
            case "search" -> new Search(this);
            case "select" -> new Select(this);
            default -> null;
        };
    }
}
