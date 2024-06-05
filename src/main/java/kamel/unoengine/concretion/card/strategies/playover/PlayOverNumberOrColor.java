package kamel.unoengine.concretion.card.strategies.playover;

import kamel.unoengine.abstraction.Card;
import kamel.unoengine.abstraction.CardPlayOverStrategy;

public class PlayOverNumberOrColor implements CardPlayOverStrategy {
    @Override
    public boolean canPlayOver(Card thisCard, Card otherCard) {
        return thisCard.getColor().color().equals(otherCard.getColor().color())
                || thisCard.getLabel().equals(otherCard.getLabel());
    }
}
