package kamel.unoengine.concretion.card.strategies.playover;

import kamel.unoengine.abstraction.Card;
import kamel.unoengine.abstraction.CardPlayOverStrategy;

public class PlayOverAll implements CardPlayOverStrategy {
    @Override
    public boolean canPlayOver(Card thisCard, Card otherCard) {
        return true;
    }
}
