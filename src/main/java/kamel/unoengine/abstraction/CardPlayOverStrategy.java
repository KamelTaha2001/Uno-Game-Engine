package kamel.unoengine.abstraction;

/**
 * Classes implementing this interface
 * define how to decide whether a card
 * can be played over another one or not.
 * One class may define the behavior that
 * is if the card label matches the other card
 * or both cards match the same color, then it
 * is allowed to play one of them over the other.
 */
public interface CardPlayOverStrategy {
    boolean canPlayOver(Card thisCard, Card otherCard);
}
