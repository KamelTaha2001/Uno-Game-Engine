package kamel.unoengine.concretion.card.strategies.action;

import kamel.unoengine.abstraction.CardActionStrategy;
import kamel.unoengine.abstraction.Game;
import kamel.unoengine.abstraction.Game.PlayDirection;

public class CardReverse implements CardActionStrategy {
    @Override
    public void performAction(Game game) {
        PlayDirection playDirection;
        if (game.getPlayDirection() == PlayDirection.CLOCKWISE)
            playDirection = PlayDirection.COUNTER_CLOCKWISE;
        else
            playDirection = PlayDirection.CLOCKWISE;
        game.setPlayDirection(playDirection);
    }
}
