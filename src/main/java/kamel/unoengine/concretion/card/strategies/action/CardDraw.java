package kamel.unoengine.concretion.card.strategies.action;

import kamel.unoengine.abstraction.Card;
import kamel.unoengine.abstraction.CardActionStrategy;
import kamel.unoengine.abstraction.Game;
import kamel.unoengine.abstraction.Player;
import kamel.unoengine.utils.Colors;
import kamel.unoengine.utils.ConsoleUtility;
import kamel.unoengine.utils.abstraction.Printer;

import java.util.ArrayList;
import java.util.List;

public class CardDraw implements CardActionStrategy {
    private int numberOfCards;

    public CardDraw(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    @Override
    public void performAction(Game game) {
        List<Card> drawnCards = new ArrayList<>(numberOfCards);
        for (int i = 0; i < numberOfCards; i++)
            drawnCards.add(game.drawSingleCard());
        Player nextPlayer = game.getNextPlayer();
        nextPlayer.giveCards(drawnCards);
        ConsoleUtility.printWithBackground(nextPlayer.getName() + " is dealt " + numberOfCards + " card/s.", Colors.YELLOW_BACKGROUND);
    }
}
