package kamel.unoengine.concretion.card.strategies.action;

import kamel.unoengine.abstraction.CardActionStrategy;
import kamel.unoengine.abstraction.Game;
import kamel.unoengine.utils.Color;

public class CardWild implements CardActionStrategy {
    @Override
    public void performAction(Game game) {
        int index = game.getCurrentPlayer().handleNumberedQuestion();
        Color color = Game.getUsedCardColors().get(index);
        game.getCardOnTable().setColor(color);
    }
}
