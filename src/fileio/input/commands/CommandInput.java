package fileio.input.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.page.NextPage;
import main.program.commands.page.PreviousPage;
import main.program.commands.page.PrintCurrentPage;
import main.program.commands.player.Backward;
import main.program.commands.player.Forward;
import main.program.commands.player.Load;
import main.program.commands.player.Next;
import main.program.commands.player.PlayPause;
import main.program.commands.player.Prev;
import main.program.commands.player.Repeat;
import main.program.commands.player.Status;
import main.program.commands.playlist.Follow;
import main.program.commands.playlist.Like;
import main.program.commands.playlist.ShowPlaylists;
import main.program.commands.stats.GetAllUsers;
import main.program.commands.stats.GetOnlineUsers;
import main.program.commands.stats.GetTop5Albums;
import main.program.commands.stats.GetTop5Artists;
import main.program.commands.stats.GetTop5Playlists;
import main.program.commands.stats.GetTop5Songs;
import main.program.commands.stats.ShowPreferredSongs;
import main.program.commands.stats.Wrapped;
import main.program.commands.user.BuyPremium;
import main.program.commands.user.CancelPremium;
import main.program.commands.user.GetNotifications;
import main.program.commands.user.LoadRecommendations;
import main.program.commands.user.SeeMerch;
import main.program.commands.user.Subscribe;
import main.program.commands.user.SwitchConnectionStatus;
import main.program.commands.user.UpdateRecommendations;
import main.program.commands.user.admin.DeleteUser;
import main.program.commands.user.admin.ShowAlbums;
import main.program.commands.user.admin.ShowPodcasts;

@Getter
@Setter
@JsonTypeInfo(use = Id.NAME, visible = true, property = "command", defaultImpl = CommandInput.class)
@JsonSubTypes({@Type(value = PlaylistOperationInput.class, names = {"addRemoveInPlaylist",
    "switchVisibility"}),
    @Type(value = CommandInputWithName.class, names = {"buyMerch", "removeAlbum", "removeEvent",
        "removePodcast", "removeAnnouncement"}),
    @Type(value = AdBreakInput.class, name = "adBreak"),
    @Type(value = ShuffleInput.class, name = "shuffle"),
    @Type(value = PlaylistCreateInput.class, name = "createPlaylist"),
    @Type(value = SearchInput.class, name = "search"),
    @Type(value = SelectInput.class, name = "select"),
    @Type(value = AddUserInput.class, name = "addUser"),
    @Type(value = ChangePageInput.class, name = "changePage"),
    @Type(value = AddAlbumInput.class, name = "addAlbum"),
    @Type(value = AddEventInput.class, name = "addEvent"),
    @Type(value = AddMerchInput.class, name = "addMerch"),
    @Type(value = AddPodcastInput.class, name = "addPodcast"),
    @Type(value = AddAnnouncementInput.class, name = "addAnnouncement")})
public class CommandInput {

    protected String command;
    protected String username;
    protected int timestamp;

    /**
     * Create a command, based on the `command` field.
     *
     * @return The associated command, or null if the command is unknown.
     */
    public Command createCommand() {
        return switch (command) {
            case "nextPage" -> new NextPage(this);
            case "previousPage" -> new PreviousPage(this);
            case "printCurrentPage" -> new PrintCurrentPage(this);
            case "backward" -> new Backward(this);
            case "forward" -> new Forward(this);
            case "load" -> new Load(this);
            case "next" -> new Next(this);
            case "playPause" -> new PlayPause(this);
            case "prev" -> new Prev(this);
            case "repeat" -> new Repeat(this);
            case "status" -> new Status(this);
            case "follow" -> new Follow(this);
            case "like" -> new Like(this);
            case "showPlaylists" -> new ShowPlaylists(this);
            case "getAllUsers" -> new GetAllUsers(this);
            case "getOnlineUsers" -> new GetOnlineUsers(this);
            case "getTop5Albums" -> new GetTop5Albums(this);
            case "getTop5Artists" -> new GetTop5Artists(this);
            case "getTop5Playlists" -> new GetTop5Playlists(this);
            case "getTop5Songs" -> new GetTop5Songs(this);
            case "showPreferredSongs" -> new ShowPreferredSongs(this);
            case "wrapped" -> new Wrapped(this);
            case "deleteUser" -> new DeleteUser(this);
            case "showAlbums" -> new ShowAlbums(this);
            case "showPodcasts" -> new ShowPodcasts(this);
            case "buyPremium" -> new BuyPremium(this);
            case "cancelPremium" -> new CancelPremium(this);
            case "getNotifications" -> new GetNotifications(this);
            case "loadRecommendations" -> new LoadRecommendations(this);
            case "seeMerch" -> new SeeMerch(this);
            case "subscribe" -> new Subscribe(this);
            case "switchConnectionStatus" -> new SwitchConnectionStatus(this);
            case "updateRecommendations" -> new UpdateRecommendations(this);
            default -> null;
        };
    }
}
