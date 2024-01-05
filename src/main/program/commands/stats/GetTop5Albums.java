package main.program.commands.stats;

import static main.program.Program.MAX_RESULTS;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import fileio.output.StatsResult.Builder;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import main.entities.audio.collections.Album;
import main.entities.audio.files.Song;
import main.entities.users.UserDatabase;
import main.program.commands.Command;

@Getter
public final class GetTop5Albums extends Command {

    private final StatsResult.Builder resultBuilder = new Builder(this);

    public GetTop5Albums(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Stream<Album> albums = UserDatabase.getInstance().getAlbums().stream();
        Stream<AlbumMeta> albumMetas = albums.map(AlbumMeta::new);

        Comparator<AlbumMeta> comparator = Comparator.comparingInt(AlbumMeta::getLikes).reversed()
            .thenComparing(albumMeta -> albumMeta.getAlbum().getName());

        List<String> top = albumMetas.sorted(comparator).limit(MAX_RESULTS)
            .map(albumMeta -> albumMeta.getAlbum().getName()).toList();

        return resultBuilder.result(top).build();
    }

    @Getter
    private static class AlbumMeta {

        private final Album album;
        private final int likes;

        AlbumMeta(final Album album) {
            this.album = album;
            this.likes = album.getSongs().stream().map(Song::getLikes).reduce(0, Integer::sum);
        }
    }
}
