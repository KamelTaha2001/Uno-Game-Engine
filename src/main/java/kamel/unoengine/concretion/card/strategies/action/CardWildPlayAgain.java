package kamel.unoengine.concretion.card.strategies.action;

import kamel.unoengine.abstraction.CardActionStrategy;
import kamel.unoengine.abstraction.Game;
import kamel.unoengine.utils.Colors;
import kamel.unoengine.utils.ConsoleUtility;

public class CardWildPlayAgain implements CardActionStrategy {
    @Override
    public void performAction(Game game) {
        new CardWild().performAction(game);
        ConsoleUtility.printWithBackground("Play again", Colors.GREEN_BACKGROUND);
        game.freezeTurnFor(1);
    }
}
