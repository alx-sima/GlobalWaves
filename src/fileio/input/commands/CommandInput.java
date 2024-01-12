package fileio.input.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.page.ChangePage;
import main.program.commands.page.NextPage;
import main.program.commands.page.PreviousPage;
import main.program.commands.page.PrintCurrentPage;
import main.program.commands.player.AdBreak;
import main.program.commands.player.Backward;
import main.program.commands.player.Forward;
import main.program.commands.player.Load;
import main.program.commands.player.Next;
import main.program.commands.player.PlayPause;
import main.program.commands.player.Prev;
import main.program.commands.player.Repeat;
import main.program.commands.player.Shuffle;
import main.program.commands.player.Status;
import main.program.commands.player.recommendations.LoadRecommendations;
import main.program.commands.player.recommendations.UpdateRecommendations;
import main.program.commands.playlist.CreatePlaylist;
import main.program.commands.playlist.Follow;
import main.program.commands.playlist.Like;
import main.program.commands.playlist.ShowPlaylists;
import main.program.commands.search.Search;
import main.program.commands.search.Select;
import main.program.commands.stats.GetAllUsers;
import main.program.commands.stats.GetOnlineUsers;
import main.program.commands.stats.GetTop;
import main.program.commands.stats.Wrapped;
import main.program.commands.user.SwitchConnectionStatus;
import main.program.commands.user.admin.AddUser;
import main.program.commands.user.admin.DeleteUser;
import main.program.commands.user.admin.ShowAlbums;
import main.program.commands.user.admin.ShowPodcasts;
import main.program.commands.user.artist.AddAlbum;
import main.program.commands.user.artist.AddEvent;
import main.program.commands.user.artist.AddMerch;
import main.program.commands.user.host.AddAnnouncement;
import main.program.commands.user.host.AddPodcast;
import main.program.commands.user.merch.SeeMerch;
import main.program.commands.user.notifications.GetNotifications;
import main.program.commands.user.notifications.Subscribe;
import main.program.commands.user.premium.BuyPremium;
import main.program.commands.user.premium.CancelPremium;

@Getter
@Setter
@JsonTypeInfo(use = Id.NAME, visible = true, property = "command", defaultImpl = CommandInput.class)
@JsonSubTypes({
    @Type(value = CommandWithNameInput.class, names = {"buyMerch", "removeAlbum", "removeEvent",
        "removePodcast", "removeAnnouncement"}),
    @Type(value = PlaylistOperationInput.class, names = {"addRemoveInPlaylist",
        "switchVisibility"}),

    @Type(value = AdBreak.Input.class, name = "adBreak"),
    @Type(value = AddAlbum.Input.class, name = "addAlbum"),
    @Type(value = AddAnnouncement.Input.class, name = "addAnnouncement"),
    @Type(value = AddEvent.Input.class, name = "addEvent"),
    @Type(value = AddMerch.Input.class, name = "addMerch"),
    @Type(value = AddPodcast.Input.class, name = "addPodcast"),
    @Type(value = AddUser.Input.class, name = "addUser"),
    @Type(value = ChangePage.Input.class, name = "changePage"),
    @Type(value = CreatePlaylist.Input.class, name = "createPlaylist"),
    @Type(value = Search.Input.class, name = "search"),
    @Type(value = Select.Input.class, name = "select"),
    @Type(value = Shuffle.Input.class, name = "shuffle"),
    @Type(value = UpdateRecommendations.Input.class, name = "updateRecommendations"),
})
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
            case "backward" -> new Backward(this);
            case "buyPremium" -> new BuyPremium(this);
            case "cancelPremium" -> new CancelPremium(this);
            case "deleteUser" -> new DeleteUser(this);
            case "follow" -> new Follow(this);
            case "forward" -> new Forward(this);
            case "getAllUsers" -> new GetAllUsers(this);
            case "getNotifications" -> new GetNotifications(this);
            case "getOnlineUsers" -> new GetOnlineUsers(this);
            case "getTop5Albums", "getTop5Artists", "getTop5Playlists", "getTop5Songs" ->
                new GetTop(this);
            case "like" -> new Like(this);
            case "load" -> new Load(this);
            case "loadRecommendations" -> new LoadRecommendations(this);
            case "next" -> new Next(this);
            case "nextPage" -> new NextPage(this);
            case "playPause" -> new PlayPause(this);
            case "prev" -> new Prev(this);
            case "previousPage" -> new PreviousPage(this);
            case "printCurrentPage" -> new PrintCurrentPage(this);
            case "repeat" -> new Repeat(this);
            case "seeMerch" -> new SeeMerch(this);
            case "showAlbums" -> new ShowAlbums(this);
            case "showPlaylists" -> new ShowPlaylists(this);
            case "showPodcasts" -> new ShowPodcasts(this);
            case "status" -> new Status(this);
            case "subscribe" -> new Subscribe(this);
            case "switchConnectionStatus" -> new SwitchConnectionStatus(this);
            case "wrapped" -> new Wrapped(this);
            default -> null;
        };
    }
}
