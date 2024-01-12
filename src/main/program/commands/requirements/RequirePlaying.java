package main.program.commands.requirements;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import main.program.commands.exceptions.InvalidOperation;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

@RequiredArgsConstructor
public final class RequirePlaying implements Requirement<Queue> {

    private final String user;
    private final int timestamp;
    private final String notPlayingError;

    @Getter
    private User caller;

    @Override
    public Queue check() throws InvalidOperation {
        caller = new RequireUserOnline(user).check();
        Player player = caller.getPlayer();
        player.updateTime(timestamp);

        Queue queue = player.getQueue();
        if (queue == null) {
            throw new InvalidOperation(notPlayingError);
        }

        return queue;
    }
}
