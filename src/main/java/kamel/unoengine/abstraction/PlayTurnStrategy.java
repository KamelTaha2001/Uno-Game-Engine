package kamel.unoengine.abstraction;

import kamel.unoengine.concretion.rule.Rule;

/**
 * Classes implementing this interface
 * define the nature of each turn played
 * by players or bots. This is done by
 * utilizing the added rules using
 * {@link Game#addGameRule(Rule)}, we check
 * for each {@link Rule} by using its
 * {@link CheckBehavior} at the appropriate
 * point in the player's turn.
 */
public interface PlayTurnStrategy {
    void playTurn(Game game, Player player);
}
