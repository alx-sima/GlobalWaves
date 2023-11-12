package main.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import main.audio.PlayerStatus;

import java.util.List;

public final class Result extends Command {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<String> results;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final PlayerStatus stats;

    public Result(final Command command, final String message, final List<String> results) {
        super(command);
        this.message = message;
        this.results = results;
        this.stats = null;
    }

    /**
     * Construct a Result that doesn't return any results.
     *
     * @param command The command that generated the result.
     * @param message The result's message.
     */
    public Result(final Command command, final String message) {
        this(command, message, null);
    }

    public Result(final Command command, final PlayerStatus stats) {
        super(command);
        this.stats = stats;
        this.message = null;
        this.results = null;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getResults() {
        return results;
    }

    public PlayerStatus getStats() {
        return stats;
    }

    @Override
    public Result execute() {
        return this;
    }
}
