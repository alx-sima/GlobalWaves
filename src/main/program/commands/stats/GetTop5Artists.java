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
import main.entities.audio.files.Song;
import main.entities.users.UserDatabase;
import main.entities.users.artist.Artist;
import main.program.commands.Command;

@Getter
public final class GetTop5Artists extends Command {

    private final StatsResult.Builder resultBuilder = new Builder(this);

    public GetTop5Artists(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Stream<Artist> artists = UserDatabase.getInstance().getArtists().stream();
        Stream<ArtistMeta> artistMetas = artists.map(ArtistMeta::new);

        Comparator<ArtistMeta> comparator = Comparator.comparingInt(ArtistMeta::getLikes).reversed()
            .thenComparing(artistMeta -> artistMeta.getArtist().getName());

        List<String> top = artistMetas.sorted(comparator).limit(MAX_RESULTS)
            .map(artistMeta -> artistMeta.getArtist().getName()).toList();
        return resultBuilder.result(top).build();
    }

    @Getter
    private static final class ArtistMeta {

        private final Artist artist;
        private final int likes;

        private ArtistMeta(final Artist artist) {
            this.artist = artist;
            this.likes = artist.getAlbums().stream().flatMap(album -> album.getSongs().stream())
                .map(Song::getLikes).reduce(0, Integer::sum);
        }
    }
}
