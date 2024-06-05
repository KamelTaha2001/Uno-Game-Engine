package kamel.unoengine.concretion.card;

import kamel.unoengine.abstraction.Card;
import kamel.unoengine.abstraction.CardPlayOverStrategy;
import kamel.unoengine.utils.Color;

/**
 * Represents any card in Uno game.
 * You can create variations between
 * the cards by using the strategy
 * setters provided in {@link Card}.
 * For example, you can use {@link Card#setPlayOverStrategy(CardPlayOverStrategy)}
 * to make specific cards act like wild cards.
 */
public class StandardUnoCard extends Card {
    public StandardUnoCard(int id, String label, Color color) {
        super(id, label, color);
    }
}
