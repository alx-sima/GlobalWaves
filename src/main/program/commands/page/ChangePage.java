package main.program.commands.page;

import fileio.input.commands.ChangePageInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.entities.audio.files.AudioFile;
import main.entities.pages.ArtistPage;
import main.entities.pages.HomePage;
import main.entities.pages.HostPage;
import main.entities.pages.LikedContentPage;
import main.entities.pages.PageHistory;
import main.entities.users.User;
import main.program.InvalidOperation;
import main.program.commands.user.OnlineUserCommand;

public final class ChangePage extends OnlineUserCommand {

    @Getter
    private final MessageResult.Builder resultBuilder = new Builder(this);
    private final String nextPage;

    public ChangePage(final ChangePageInput input) {
        super(input);
        nextPage = input.getNextPage();
    }

    @Override
    public MessageResult execute(final User caller) {
        PageHistory history = caller.getPageHistory();
        AudioFile nowPlaying = caller.getPlayer().getPlayingAt(timestamp);

        try {
            switch (nextPage) {
                case "Home":
                    history.changePage(new HomePage(caller));
                    break;
                case "LikedContent":
                    history.changePage(new LikedContentPage(caller));
                    break;
                case "Host":
                    history.changePage(new HostPage(nowPlaying.getHost()));
                    break;
                case "Artist":
                    history.changePage(new ArtistPage(nowPlaying.getArtist()));
                    break;
                default:
                    return resultBuilder.returnMessage(
                        user + " is trying to access a non-existent page.");
            }
        } catch (InvalidOperation e) {
            return resultBuilder.returnMessage(user + " is trying to access a non-existent page.");
        }
        return resultBuilder.returnMessage(user + " accessed " + nextPage + " successfully.");
    }
}
