package main.program.commands.player;

import fileio.input.commands.CommandInput;
import lombok.AllArgsConstructor;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

public final class SkipTime extends NoOutputCommand {

    private static final int SKIP_TIME = 90;

    private static final String BACKWARD_ERROR_MESSAGE = "Please load a source before rewinding.";
    private static final String BACKWARD_SUCCESS_MESSAGE = "Rewound successfully.";
    private static final String FORWARD_ERROR_MESSAGE = "Please load a source before attempting to forward";
    private static final String FORWARD_SUCCESS_MESSAGE = "Skipped forward successfully.";

    public SkipTime(final CommandInput input) {
        super(input);
    }


    @Override
    protected String executeNoOutput() throws InvalidOperation {
        Command commandExecutor = switch (command) {
            case "backward" ->
                new Command(-SKIP_TIME, BACKWARD_ERROR_MESSAGE, BACKWARD_SUCCESS_MESSAGE);
            case "forward" ->
                new Command(SKIP_TIME, FORWARD_ERROR_MESSAGE, FORWARD_SUCCESS_MESSAGE);
            default -> throw new InvalidOperation();
        };

        return commandExecutor.execute();
    }

    @AllArgsConstructor
    private final class Command {

        private final int skipTime;
        private final String errorMessage;
        private final String successMessage;

        public String execute() throws InvalidOperation {
            User caller = new RequireUserOnline(user).check();
            Player player = caller.getPlayer();
            player.updateTime(timestamp);

            Queue queue = player.getQueue();
            if (queue == null) {
                return "Please load a source before attempting to forward.";
            }

            try {
                queue.skip(skipTime);
                return successMessage;
            } catch (InvalidOperation e) {
                return errorMessage;
            }
        }
    }
}
