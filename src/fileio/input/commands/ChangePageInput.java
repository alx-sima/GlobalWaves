package fileio.input.commands;

import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.page.ChangePage;

@Getter
@Setter
public final class ChangePageInput extends CommandInput {

    private String nextPage;

    @Override
    public Command createCommand() {
        return new ChangePage(this);
    }
}
