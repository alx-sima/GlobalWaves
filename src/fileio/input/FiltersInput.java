package fileio.input;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class FiltersInput {

    private String name;
    private String album;
    private List<String> tags;
    private String lyrics;
    private String genre;
    private String releaseYear;
    private String artist;
    private String owner;

    public FiltersInput() {
    }
}
