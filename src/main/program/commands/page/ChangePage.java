package main.program.commands.page;

import fileio.input.commands.ChangePageInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.files.AudioFile;
import main.program.entities.users.User;
import main.program.entities.users.interactions.pages.ArtistPage;
import main.program.entities.users.interactions.pages.HomePage;
import main.program.entities.users.interactions.pages.HostPage;
import main.program.entities.users.interactions.pages.LikedContentPage;
import main.program.entities.users.interactions.pages.PageHistory;

public final class ChangePage extends Command {

    @Getter
    private final MessageResult.Builder resultBuilder = new Builder(this);
    private final String nextPage;

    public ChangePage(final ChangePageInput input) {
        super(input);
        nextPage = input.getNextPage();
    }

    @Override
    public MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        PageHistory history = caller.getPageHistory();

            switch (nextPage) {
                case "Home":
                    history.changePage(new HomePage(caller));
                    break;
                case "LikedContent":
                    history.changePage(new LikedContentPage(caller));
                    break;
                case "Host", "Artist":
                    AudioFile nowPlaying = caller.getPlayer().getPlayingAt(timestamp);
                    if (nowPlaying == null) {
                        return resultBuilder.returnMessage(
                            user + " is trying to access a non-existent page.");
                    }

                    String creatorName = nowPlaying.getOwner();
                    if (nextPage.equals("Host")) {
                        history.changePage(new HostPage(creatorName));
                    } else {
                        history.changePage(new ArtistPage(creatorName));
                    }
                    break;
                default:
                    return resultBuilder.returnMessage(
                        user + " is trying to access a non-existent page.");
            }
        return resultBuilder.returnMessage(user + " accessed " + nextPage + " successfully.");
    }
}
