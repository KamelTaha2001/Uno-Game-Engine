package kamel.unoengine.concretion.player;

import kamel.unoengine.abstraction.*;
import kamel.unoengine.concretion.player.strategies.playturn.OptionalDrawRealPlayerTurn;
import kamel.unoengine.concretion.player.strategies.question.PlayerNumberedQuestionHandling;
import kamel.unoengine.utils.Colors;
import kamel.unoengine.utils.ConsoleUtility;
import kamel.unoengine.utils.abstraction.Printer;

public class RealPlayer extends Player {
    public RealPlayer(Game game) {
        super(game, new OptionalDrawRealPlayerTurn());
        setNumberedQuestionStrategy(new PlayerNumberedQuestionHandling());
    }

    @Override
    public Card chooseCard() {
        int cardId = ConsoleUtility.promptToChooseCard();
        Card chosenCard = null;
        while (chosenCard == null) {
            try {
                chosenCard = getCardById(cardId);
                if (chosenCard.canBePlayedOver(getGame().getCardOnTable()))
                    return chosenCard;
                else {
                    ConsoleUtility.printWithBackground("Can't play this card!", Colors.RED_BACKGROUND);
                    chosenCard = chooseCard();
                }
            } catch (NullPointerException e) {
                ConsoleUtility.printWithBackground(e.getMessage(), Colors.RED_BACKGROUND);
                chosenCard = chooseCard();
            }
        }
        return chosenCard;
    }


    @Override
    public String askForName() {
        return ConsoleUtility.promptForName();
    }
}
