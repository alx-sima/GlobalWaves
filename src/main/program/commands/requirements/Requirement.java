package main.program.commands.requirements;

import main.program.commands.exceptions.InvalidOperation;

public interface Requirement<T> {

    /**
     * Check the requirement. If satisfied, return the required target, else throw an error.
     *
     * @throws InvalidOperation if the requirement is not satisfied. The error message contains the
     * reason.
     * @return whether the requirement is satisfied.
     */
    T check() throws InvalidOperation;
}
