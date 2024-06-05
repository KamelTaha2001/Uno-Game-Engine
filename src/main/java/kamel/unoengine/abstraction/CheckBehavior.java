package kamel.unoengine.abstraction;

import kamel.unoengine.concretion.rule.Rule;

/**
 * This interface is used for defining how a specific
 * rule checks for its validity, the rule must be added to the
 * {@link Game} object using {@link Game#addGameRule(Rule)}.
 */
@FunctionalInterface
public interface CheckBehavior {
    boolean check(Game game);
}
