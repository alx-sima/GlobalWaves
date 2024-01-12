package main.program.commands.user.artist;

import fileio.input.commands.CommandWithNameInput;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.entities.users.creators.Artist;
import main.program.entities.users.creators.content.Event;

public final class AddEvent extends ArtistCommand {

    /**
     * Regex pattern that matches the `dd-mm-yyyy` date format.
     */
    private static final Pattern DATE_PATTERN = Pattern.compile(
        "(?<day>\\d{2})-(?<month>\\d{2})-(?<year>\\d{4})");
    private static final int DAYS_IN_MONTH = 31;
    private static final int MONTHS_IN_YEAR = 12;
    private final String name;
    private final String description;
    private final String date;

    public AddEvent(final Input input) {
        super(input);
        this.name = input.getName();
        this.description = input.getDescription();
        this.date = input.getDate();
    }

    /**
     * Checks if the given date is valid.
     */
    private static boolean isValidDate(final int day, final int month, final int year) {
        if (day < 1 || day > DAYS_IN_MONTH) {
            return false;
        }
        if (month < 1 || month > MONTHS_IN_YEAR) {
            return false;
        }
        return year >= 0;
    }

    /**
     * Checks if the given date format is valid.
     *
     * @param dateFormat a string possibly representing a date.
     */
    private static boolean isValidDateFormat(final String dateFormat) {
        Matcher matcher = DATE_PATTERN.matcher(dateFormat);
        if (!matcher.matches()) {
            return false;
        }
        try {
            int day = Integer.parseInt(matcher.group("day"));
            int month = Integer.parseInt(matcher.group("month"));
            int year = Integer.parseInt(matcher.group("year"));

            return isValidDate(day, month, year);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected String returnExecutionMessage(final Artist artist) {
        if (!isValidDateFormat(date)) {
            return "Event for " + user + " does not have a valid date.";
        }

        artist.addEvent(new Event(user, name, description, date));
        return user + " has added new event successfully.";
    }

    @Getter
    @Setter
    public static final class Input extends CommandWithNameInput {

        private String description;
        private String date;

        @Override
        public Command createCommand() {
            return new AddEvent(this);
        }
    }
}
