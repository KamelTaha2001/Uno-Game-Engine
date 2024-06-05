package kamel.unoengine.concretion.card.strategies.action;

import kamel.unoengine.abstraction.CardActionStrategy;
import kamel.unoengine.abstraction.Game;

public class CardSkip implements CardActionStrategy {
    @Override
    public void performAction(Game game) {
        game.nextPlayerTurn();
    }
}
