package fileio.input.commands;

import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.player.recommendations.UpdateRecommendations;

@Getter
@Setter
public final class UpdateRecommendationsInput extends CommandInput{

    private  String recommendationType;

    @Override
    public Command createCommand() {
        return new UpdateRecommendations(this);
    }
}
