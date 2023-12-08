package fileio.output;

/**
 * Interface for building command results.
 */
public interface ResultBuilder {

    /**
     * Sets the message of the result.
     *
     * @param message the message to be set.
     * @return the updated builder.
     */
    ResultBuilder withMessage(String message);

    /**
     * Builds the result.
     */
    CommandResult build();
}
