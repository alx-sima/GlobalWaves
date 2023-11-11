package main.commands;

public class Select extends Command {
    private final int itemNumber;

    public Select(final String command, final String user, final int timestamp,
                  final int itemNumber) {
        super(command, user, timestamp);
        this.itemNumber = itemNumber;
    }
}
