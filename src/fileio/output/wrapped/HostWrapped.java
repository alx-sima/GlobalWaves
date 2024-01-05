package fileio.output.wrapped;

import java.util.Map;
import lombok.Getter;
import main.entities.users.host.Host;

@Getter
public final class HostWrapped implements WrappedOutput {

    private final Map<Pair, Integer> topEpisodes;
    private final int listeners;

    public HostWrapped(final Host.Wrapped wrapped) {
        topEpisodes = WrappedOutput.getTop(wrapped.getTopEpisodes());
        listeners = wrapped.getTopListeners().size();
    }

    @Override
    public boolean checkEmpty() {
        return topEpisodes.isEmpty() && listeners == 0;
    }
}
