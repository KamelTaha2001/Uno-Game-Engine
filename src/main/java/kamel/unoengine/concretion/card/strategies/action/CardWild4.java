package kamel.unoengine.concretion.card.strategies.action;

import kamel.unoengine.abstraction.CardActionStrategy;
import kamel.unoengine.abstraction.Game;

public class CardWild4 implements CardActionStrategy {
    @Override
    public void performAction(Game game) {
        new CardWild().performAction(game);
        new CardDraw(4).performAction(game);
    }
}
