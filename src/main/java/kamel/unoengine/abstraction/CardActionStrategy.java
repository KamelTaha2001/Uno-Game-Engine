package kamel.unoengine.abstraction;

/**
 * Classes implementing this interface
 * must define the effect of playing a
 * card.
 * The card might have no action, such
 * as playing a number card.
 * Or it might penalize the next player
 * by drawing 2 cards, and so on.
 */
public interface CardActionStrategy {
    void performAction(Game game);
}
