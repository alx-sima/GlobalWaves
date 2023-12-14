package main.program.commands.user.artist;

import fileio.input.commands.AddEventInput;
import fileio.output.builders.ResultBuilder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.entities.users.artist.Artist;
import main.entities.users.artist.Event;

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

    public AddEvent(final AddEventInput input) {
        super(input);
        this.name = input.getName();
        this.description = input.getDescription();
        this.date = input.getDate();
    }

    /**
     * Checks if the given date is valid.
     */
    private boolean isValidDate(final int day, final int month, final int year) {
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
    private boolean isValidDateFormat(final String dateFormat) {
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
    protected ResultBuilder execute(final Artist artist) {
        if (!isValidDateFormat(date)) {
            return getResultBuilder().withMessage(
                "Event for " + user + " does not have a valid date.");
        }

        Event event = new Event(user, name, description, date);
        artist.getEvents().add(event);
        return getResultBuilder().withMessage(user + " has added new event successfully.");
    }
}
