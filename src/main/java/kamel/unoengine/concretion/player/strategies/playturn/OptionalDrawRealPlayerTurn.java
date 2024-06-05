package kamel.unoengine.concretion.player.strategies.playturn;

import kamel.unoengine.abstraction.Game;
import kamel.unoengine.abstraction.PlayTurnStrategy;
import kamel.unoengine.abstraction.Player;
import kamel.unoengine.concretion.comment.Comments;
import kamel.unoengine.concretion.rule.Rules;
import kamel.unoengine.utils.Colors;
import kamel.unoengine.utils.ConsoleUtility;

import java.util.List;

public class OptionalDrawRealPlayerTurn implements PlayTurnStrategy {
    @Override
    public void playTurn(Game game, Player player) {
        ConsoleUtility.printTurnSeparator(player.getName(), game.getCardOnTable());
        ConsoleUtility.printCards(player.getCards());
        try {
            player.promptForComment(List.of(Comments.PASS.getCommentText(), Comments.DRAW.getCommentText()));
            player.validateAndHandleRule(Rules.DRAW.toRule());
            if (player.validateAndHandleRule(Rules.PASS.toRule()))
                return;
            if (!player.canPlayAnyCards()) {
                ConsoleUtility.printWithBackground("Can't play any cards!", Colors.RED_BACKGROUND);
                return;
            }
            game.getDiscardStrategy().discard(game);
            if (player.getNumberOfCards() == 1)
                player.validateAndHandleRule(Rules.SAY_UNO.toRule());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
