package kamel.unoengine.abstraction;

import kamel.unoengine.concretion.rule.Rule;

/**
 * This interface is used for defining the consequences
 * of breaking a rule. The rule must be added to the
 * {@link Game} object using {@link Game#addGameRule(Rule)}.
 */
@FunctionalInterface
public interface ApplyBehavior {
    void apply(Game game);
}
