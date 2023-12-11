package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import main.entities.audio.collections.Album;
import main.entities.audio.files.Song;
import main.program.Library;
import main.program.commands.Command;

public final class GetTop5Albums extends Command {

    public GetTop5Albums(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Stream<Album> albums = Library.getInstance().getAlbums().stream();
        Stream<AlbumMeta> albumMetas = albums.map(AlbumMeta::new);

        List<String> top = albumMetas.sorted(
                Comparator.comparingInt(AlbumMeta::getLikes).reversed()).limit(MAX_RESULTS)
            .map(albumMeta -> albumMeta.getAlbum().getName()).toList();
        return new StatsResult(this, top);
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
