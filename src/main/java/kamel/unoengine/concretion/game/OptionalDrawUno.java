package kamel.unoengine.concretion.game;

import kamel.unoengine.abstraction.Game;
import kamel.unoengine.concretion.deck.SimpleDeck;
import kamel.unoengine.concretion.deck.strategies.draw.DrawNumberOfCards;
import kamel.unoengine.concretion.game.strategies.DiscardNumberOfCards;
import kamel.unoengine.concretion.player.BotPlayer;
import kamel.unoengine.concretion.player.RealPlayer;
import kamel.unoengine.concretion.rule.Rules;
import kamel.unoengine.concretion.rule.Rule;
import kamel.unoengine.utils.ConsoleUtility;

public class OptionalDrawUno extends Game {
    public OptionalDrawUno() {
        super(new SimpleDeck(), new DiscardNumberOfCards(1), new DrawNumberOfCards(1));
    }

    @Override
    public void play() {
        createPlayers();
        addRules();
        setInitialCards(3);
        dealCards();
        gameLoop();
    }

    private void gameLoop() {
        while (true) {
            askPlayerToPlayTurn();
            if (validateRule(Rules.WIN.getRuleName())) {
                handleRule(Rules.WIN.getRuleName());
                break;
            }
            nextPlayerTurn();
        }
    }

    private void createPlayers() {
        int numOfRealPlayers = ConsoleUtility.promptForNumberOfRealPlayers();
        int numOfBots = ConsoleUtility.promptForNumberOfBots();
        for (int i = 0; i < numOfRealPlayers; i++) {
            new RealPlayer(this);
        }
        for (int i = 0; i < numOfBots; i++) {
            new BotPlayer(this);
        }
    }

    private void addRules() {
        addGameRule(new Rule(
                Rules.SAY_UNO.getRuleName(),
                Rules.SAY_UNO.getRuleValidator(),
                Rules.SAY_UNO.getHandler()
        ));
        addGameRule(new Rule(
                Rules.WIN.getRuleName(),
                Rules.WIN.getRuleValidator(),
                Rules.WIN.getHandler()
        ));
        addGameRule(new Rule(
                Rules.PASS.getRuleName(),
                Rules.PASS.getRuleValidator(),
                Rules.PASS.getHandler()
        ));
        addGameRule(new Rule(
                Rules.DRAW.getRuleName(),
                Rules.DRAW.getRuleValidator(),
                Rules.DRAW.getHandler()
        ));
    }
}
