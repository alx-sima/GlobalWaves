package main.program.commands.user.artist;

import fileio.input.commands.AddEventInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.Event;
import main.audio.collections.Library;
import main.program.Program;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.IsArtistDependency;

public final class AddEvent extends DependentCommand {

    /**
     * Regex pattern that matches the `dd-mm-yyyy` date format.
     */
    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{2})-(\\d{2})-(\\d{4})");

    private final MessageResultBuilder resultBuilder;
    private final String name;
    private final String description;
    private final String date;

    public AddEvent(final AddEventInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        this.name = input.getName();
        this.description = input.getDescription();
        this.date = input.getDate();
    }

    private boolean isValidDate(final String dateFormat) {
        Matcher matcher = DATE_PATTERN.matcher(dateFormat);
        if (!matcher.matches()) {
            return false;
        }
        try {
            int day = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));

            if (day < 1 || day > 31) {
                return false;
            }
            if (month < 1 || month > 12) {
                return false;
            }
            if (year < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public CommandResult checkDependencies() {
        IsArtistDependency dependency = new IsArtistDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Program program = Program.getInstance();
        Library library = program.getLibrary();
        if (!isValidDate(date)) {
            return resultBuilder.withMessage("Invalid date!");
        }

        Event event = new Event(user, name, description, date);
        library.getEvents().put(user, event);
        return resultBuilder.withMessage(user + " has added new event successfully.");
    }
}
