package kamel.unoengine.abstraction;

import java.util.List;

/**
 * Classes implementing this interface
 * define the nature of cards included
 * in the deck (in the game also).
 * Any new cards, modifications on the
 * number of cards, or the way of shuffling
 * the cards must be defined inside one of
 * these classes.
 */
public interface DeckGenerationStrategy {
    List<Card> generate();
    void addUsedColors();
}
