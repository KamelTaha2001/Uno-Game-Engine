package kamel.unoengine.concretion.game.strategies;

import kamel.unoengine.abstraction.CardDiscardStrategy;
import kamel.unoengine.abstraction.Game;

public class DiscardNumberOfCards implements CardDiscardStrategy {
    private int numberOfCards;

    public DiscardNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    @Override
    public void discard(Game game) {
        for (int i = 0; i < numberOfCards; i++) {
            game.getCurrentPlayer().discardCard();
        }
    }
}
