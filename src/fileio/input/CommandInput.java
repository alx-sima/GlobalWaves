package fileio.input;

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
import main.program.commands.playlist.Like;
import main.program.commands.playlist.ShowPlaylists;
import main.program.commands.playlist.ShowPreferredSongs;
import main.program.commands.search.Search;
import main.program.commands.search.Select;

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

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public FiltersInput getFilters() {
        return filters;
    }

    public void setFilters(final FiltersInput filters) {
        this.filters = filters;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(final int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(final int seed) {
        this.seed = seed;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(final int playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    /**
     * Create a command, based on the `type` field.
     *
     * @return The associated command.
     */
    public Command createCommand() {
        return switch (command) {
            case "search" -> new Search(this);
            case "select" -> new Select(this);
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
            case "like" -> new Like(this);
            case "showPlaylists" -> new ShowPlaylists(this);
            case "showPreferredSongs" -> new ShowPreferredSongs(this);
            default -> null;
        };
    }
}
