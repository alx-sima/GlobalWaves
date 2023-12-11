package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import main.entities.audio.files.Song;
import main.program.Library;
import main.program.commands.Command;

public final class GetTop5Songs extends Command {

    public GetTop5Songs(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Library library = Library.getInstance();
        Stream<Song> songs = library.getSongs().stream();
        Stream<Song> albumSongs = library.getAlbums().stream()
            .flatMap(album -> album.getSongs().stream());

        // Compare by number of likes in descending order.
        Comparator<Song> comparator = Comparator.comparingInt(Song::getLikes).reversed();

        List<String> top = Stream.concat(songs, albumSongs).sorted(comparator).limit(MAX_RESULTS)
            .map(Song::getName).toList();

        return new StatsResult(this, top);
    }
}
