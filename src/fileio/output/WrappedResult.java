package fileio.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import fileio.output.wrapped.WrappedOutput;
import lombok.Getter;
import main.program.commands.Command;

@Getter
public final class WrappedResult extends MessageResult {

    @JsonInclude(Include.NON_NULL)
    private final WrappedOutput result;

    private WrappedResult(final Builder builder) {
        super(builder);
        result = builder.result;
    }

    public static final class Builder extends MessageResult.Builder {

        private WrappedOutput result;

        public Builder(final Command cmd) {
            super(cmd);
        }

        /**
         * Set the result.
         */
        public Builder result(final WrappedOutput wrapped) {
            result = wrapped;
            return this;
        }

        @Override
        public WrappedResult build() {
            return new WrappedResult(this);
        }
    }
}
