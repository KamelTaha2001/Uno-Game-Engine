package kamel.unoengine.abstraction;

/**
 * Classes implementing this interface
 * define how the player discards cards.
 * Normally he would discard a single card.
 * But you might decide that he must discard multiple ones.
 */
public interface CardDiscardStrategy {
    void discard(Game game);
}
