package main.program.commands.dependencies;

import fileio.output.ResultBuilder;
import main.program.commands.DependentCommand;

public abstract class CommandDependency extends DependentCommand {

    protected final DependentCommand chainedCommand;
    protected final ResultBuilder resultBuilder;

    protected CommandDependency(final DependentCommand command, final ResultBuilder resultBuilder) {
        super(command);
        this.chainedCommand = command;
        this.resultBuilder = resultBuilder;
    }
}
