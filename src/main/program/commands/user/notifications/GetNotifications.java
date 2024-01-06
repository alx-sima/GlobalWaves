package main.program.commands.user.notifications;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.ResultBuilder;
import java.util.List;
import lombok.Getter;
import main.program.entities.users.User;
import main.program.commands.Command;
import main.program.commands.user.OnlineUserCommand;
import main.program.entities.users.interactions.notifications.Notification;

@Getter
public final class GetNotifications extends OnlineUserCommand {

    private final NotificationsResult.Builder resultBuilder = new NotificationsResult.Builder(this);

    public GetNotifications(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
        List<Notification> notifications = caller.getNotifications();

        return resultBuilder.notifications(notifications).build();
    }

    @Getter
    private static final class NotificationsResult extends MessageResult {

        private final List<Notification> notifications;

        private NotificationsResult(final Builder builder) {
            super(builder);
            notifications = builder.notifications;
        }

        private static final class Builder extends ResultBuilder {

            private List<Notification> notifications;

            public Builder(final Command cmd) {
                super(cmd);
            }

            /**
             * Set the notifications.
             */
            public Builder notifications(final List<Notification> notificationsList) {
                notifications = notificationsList;
                return this;
            }

            @Override
            public MessageResult build() {
                return new NotificationsResult(this);
            }
        }
    }
}
