package main.program.commands;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.commands.exceptions.InvalidOperation;

/**
 * A command that does not display any output besides maybe a message.
 */
@Getter
public abstract class NoOutputCommand extends Command {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    protected NoOutputCommand(final CommandInput input) {
        super(input);
    }

    @Override
    public final CommandResult execute() throws InvalidOperation {
        return resultBuilder.returnMessage(executeNoOutput());
    }

    /**
     * Executes the command and returns the message to be displayed.
     *
     * @return the message to be displayed after the command is executed successfully.
     * @throws InvalidOperation if the command is invalid.
     */
    protected abstract String executeNoOutput() throws InvalidOperation;
}
