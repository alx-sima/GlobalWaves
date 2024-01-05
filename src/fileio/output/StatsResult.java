package fileio.output;

import java.util.List;
import lombok.Getter;
import main.program.commands.Command;

@Getter
public final class StatsResult extends MessageResult {

    private final List<String> result;

    private StatsResult(final Builder builder) {
        super(builder);
        result = builder.result;
    }

    public static final class Builder extends ResultBuilder {

        private List<String> result;

        public Builder(final Command cmd) {
            super(cmd);
        }

        /**
         * Set the result.
         */
        public Builder result(final List<String> stats) {
            this.result = stats;
            return this;
        }

        @Override
        public MessageResult build() {
            return new StatsResult(this);
        }
    }
}
