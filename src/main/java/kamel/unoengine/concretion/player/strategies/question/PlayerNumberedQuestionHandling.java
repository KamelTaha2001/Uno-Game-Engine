package kamel.unoengine.concretion.player.strategies.question;

import kamel.unoengine.abstraction.Game;
import kamel.unoengine.abstraction.NumberedQuestionStrategy;
import kamel.unoengine.utils.ConsoleUtility;

public class PlayerNumberedQuestionHandling implements NumberedQuestionStrategy {
    @Override
    public int handle(Game game) {
        return ConsoleUtility.promptToChooseColor(Game.getUsedCardColors());
    }
}
