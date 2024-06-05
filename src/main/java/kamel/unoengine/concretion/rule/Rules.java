package kamel.unoengine.concretion.rule;

import kamel.unoengine.abstraction.ApplyBehavior;
import kamel.unoengine.abstraction.Card;
import kamel.unoengine.abstraction.CheckBehavior;
import kamel.unoengine.abstraction.Player;
import kamel.unoengine.concretion.comment.Comments;
import kamel.unoengine.concretion.deck.strategies.draw.DrawNumberOfCards;
import kamel.unoengine.utils.Colors;
import kamel.unoengine.utils.ConsoleUtility;
import kamel.unoengine.utils.Constants;

import java.util.Comparator;
import java.util.List;

public enum Rules {
    SAY_UNO("Uno",
            game -> {
                game.getCurrentPlayer().promptForComment(List.of(Comments.UNO.getCommentText()));
                return !game.getCurrentPlayer().didComment(Comments.UNO.getCommentText())
                        && game.getCurrentPlayer().getNumberOfCards() == 1;
            },
            game ->  {
                game.getCurrentPlayer().giveCards(new DrawNumberOfCards(2).draw(game.getDeck()));
                ConsoleUtility.printWithBackground(Constants.SAY_UNO_ERROR, Colors.RED_BACKGROUND);
                ConsoleUtility.printCards(game.getCurrentPlayer().getCards());
                game.getCurrentPlayer().uncomment(Comments.UNO.getCommentText());
            }
            ),
    PASS("Pass",
            game -> game.getCurrentPlayer().didComment(Comments.PASS.getCommentText()),
            game -> game.getCurrentPlayer().uncomment(Comments.PASS.getCommentText())
            ),
    DRAW("Draw",
            game -> game.getCurrentPlayer().didComment(Comments.DRAW.getCommentText()),
            game -> {
                List<Card> drawnCards = game.draw();
                game.getCurrentPlayer().giveCards(drawnCards);
                ConsoleUtility.printWithBackground("You have drawn " + drawnCards.size() + " cards.", Colors.RED_BACKGROUND);
                ConsoleUtility.printCards(game.getCurrentPlayer().getCards());
                game.getCurrentPlayer().promptForComment(List.of(Comments.PASS.getCommentText()));
                game.getCurrentPlayer().uncomment(Comments.DRAW.getCommentText());
            }
    ),
    WIN("Win",
            game -> game.getCurrentPlayer().getNumberOfCards() == 0,
            game -> {
                List<Player> players = game.getSortedPlayers(Comparator.comparingInt(Player::getNumberOfCards));
                ConsoleUtility.printResults(players);
            }
    );

    private String ruleName;
    private CheckBehavior ruleValidator;
    private ApplyBehavior handler;

    Rules(String ruleName, CheckBehavior ruleValidator, ApplyBehavior handler) {
        this.ruleName = ruleName;
        this.ruleValidator = ruleValidator;
        this.handler = handler;
    }

    public String getRuleName() {
        return ruleName;
    }

    public CheckBehavior getRuleValidator() {
        return ruleValidator;
    }

    public ApplyBehavior getHandler() {
        return handler;
    }

    public Rule toRule() {
        return new Rule(
                ruleName,
                ruleValidator,
                handler
        );
    }
}
