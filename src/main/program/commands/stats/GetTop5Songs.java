package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.builders.StatsResultBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import main.entities.audio.files.Song;
import main.entities.users.UserDatabase;
import main.program.Library;
import main.program.commands.Command;

@Getter
public final class GetTop5Songs extends Command {

    private final StatsResultBuilder resultBuilder = new StatsResultBuilder().withCommand(this);

    public GetTop5Songs(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Stream<Song> songs = Library.getInstance().getSongs().stream();
        Stream<Song> albumSongs = UserDatabase.getInstance().getAlbums().stream()
            .flatMap(album -> album.getSongs().stream());

        // Compare by number of likes in descending order.
        Comparator<Song> comparator = Comparator.comparingInt(Song::getLikes).reversed()
            .thenComparingInt(Song::getCreationTime);

        List<String> top = Stream.concat(songs, albumSongs).sorted(comparator).limit(MAX_RESULTS)
            .map(Song::getName).toList();

        return resultBuilder.withResult(top).build();
    }
}
