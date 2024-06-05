package kamel.unoengine.concretion.player.strategies.playturn;

import kamel.unoengine.abstraction.Card;
import kamel.unoengine.abstraction.Game;
import kamel.unoengine.abstraction.PlayTurnStrategy;
import kamel.unoengine.abstraction.Player;
import kamel.unoengine.utils.Colors;
import kamel.unoengine.utils.ConsoleUtility;
import kamel.unoengine.utils.abstraction.Printer;

import java.util.List;

public class OptionalDraBotPlayerTurn implements PlayTurnStrategy {
    @Override
    public void playTurn(Game game, Player player) {
        ConsoleUtility.printTurnSeparator(player.getName(), game.getCardOnTable());
        if (player.canPlayAnyCards()) {
            player.discardCard();
            Printer.getInstance().println("Played: " + game.getCardOnTable(), Colors.WHITE);
        } else {
            List<Card> drawnCards = game.draw();
            for (Card c : drawnCards)
                player.giveCard(c);
            if (player.canPlayAnyCards()) {
                player.discardCard();
                Printer.getInstance().println("Played: " + game.getCardOnTable(), Colors.WHITE);
            } else {
                Printer.getInstance().println("");
                Printer.getInstance().print("Can't play any cards!", Colors.WHITE, Colors.RED_BACKGROUND);
                Printer.getInstance().println("");
            }
        }
        Printer.getInstance().println(player.getName() + " has " + player.getNumberOfCards() + " cards.", Colors.YELLOW);
        if (player.getNumberOfCards() == 1)
            Printer.getInstance().println("Uno", Colors.WHITE);
    }
}
