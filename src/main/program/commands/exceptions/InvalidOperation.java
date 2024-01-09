package main.program.commands.exceptions;

public class InvalidOperation extends Exception {

    private final String message;

    public InvalidOperation(final String message) {
        this.message = message;
    }

    public InvalidOperation() {
        this(null);
    }
}
