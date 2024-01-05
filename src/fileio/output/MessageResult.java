package fileio.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import main.program.commands.Command;

@Getter
public class MessageResult extends CommandResult {

    @JsonInclude(Include.NON_NULL)
    protected final String message;

    protected MessageResult(final ResultBuilder builder) {
        super(builder.command, builder.user, builder.timestamp);
        message = builder.message;
    }

    public static class Builder extends ResultBuilder {

        public Builder(final Command cmd) {
            super(cmd);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MessageResult build() {
            return new MessageResult(this);
        }
    }
}
