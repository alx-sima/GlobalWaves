package main.program.commands;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;

@Getter
public abstract class DefaultOutputCommand extends Command {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    protected DefaultOutputCommand(final CommandInput input) {
        super(input);
    }

    @Override
    public final CommandResult execute() {
        return resultBuilder.returnMessage(returnExecutionMessage());
    }

    protected abstract String returnExecutionMessage();
}
