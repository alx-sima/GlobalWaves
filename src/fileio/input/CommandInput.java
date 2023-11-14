package fileio.input;

import main.commands.Command;
import main.commands.player.Load;
import main.commands.player.PlayPause;
import main.commands.player.Status;
import main.commands.search.Search;
import main.commands.search.Select;

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
            case "search" -> new Search(command, username, timestamp, getType(), getFilters());
            case "select" -> new Select(command, username, timestamp, getItemNumber());
            case "load" -> new Load(command, username, timestamp);
            case "playPause" -> new PlayPause(command, username, timestamp);
            case "status" -> new Status(command, username, timestamp);
            default -> null;
        };
    }
}
