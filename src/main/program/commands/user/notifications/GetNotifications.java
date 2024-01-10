package main.program.commands.user.notifications;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.ResultBuilder;
import java.util.List;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.interactions.notifications.Notification;

@Getter
public final class GetNotifications extends Command {

    private final NotificationsResult.Builder resultBuilder = new NotificationsResult.Builder(this);

    public GetNotifications(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        List<Notification> notifications = new RequireUserOnline(user).check().getNotifications();
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
