package kamel.unoengine.abstraction;

import java.util.List;

/**
 * Classes implementing this interface
 * define how the player draws cards
 * when he comments to draw a card
 * from the deck.
 * Normally the player would
 */
public interface DrawStrategy {
    List<Card> draw(Deck deck);
}
