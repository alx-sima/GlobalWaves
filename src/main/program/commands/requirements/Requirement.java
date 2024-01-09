package main.program.commands.requirements;

import main.program.commands.exceptions.InvalidOperation;

public interface Requirement<T> {

    /**
     * Check the requirement. If satisfied, set the required target, else set an error message.
     *
     * @return whether the requirement is satisfied.
     */
    public abstract T check() throws InvalidOperation;
}
