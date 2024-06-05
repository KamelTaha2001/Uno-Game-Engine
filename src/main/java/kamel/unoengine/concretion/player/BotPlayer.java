package kamel.unoengine.concretion.player;

import kamel.unoengine.abstraction.Card;
import kamel.unoengine.abstraction.Game;
import kamel.unoengine.abstraction.Player;
import kamel.unoengine.concretion.player.strategies.playturn.OptionalDraBotPlayerTurn;
import kamel.unoengine.concretion.player.strategies.question.BotNumberedQuestionHandling;
import java.util.Iterator;

public class BotPlayer extends Player {
    private static final int botId = 1;

    public BotPlayer(Game game) {
        super(game, new OptionalDraBotPlayerTurn());
        setNumberedQuestionStrategy(new BotNumberedQuestionHandling());
    }

    @Override
    public Card chooseCard() {
        Iterator<Card> cards = getCards();
        while (cards.hasNext()) {
            Card card = cards.next();
            if (card.canBePlayedOver(getGame().getCardOnTable()))
                return card;
        }
        return null;
    }

    @Override
    public String askForName() {
        return "BOT-" + botId;
    }
}
