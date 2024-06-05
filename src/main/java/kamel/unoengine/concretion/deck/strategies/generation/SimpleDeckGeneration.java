package kamel.unoengine.concretion.deck.strategies.generation;

import kamel.unoengine.abstraction.*;
import kamel.unoengine.concretion.card.StandardUnoCard;
import kamel.unoengine.concretion.card.strategies.action.*;
import kamel.unoengine.concretion.card.strategies.playover.PlayOverAll;
import kamel.unoengine.concretion.card.strategies.playover.PlayOverNumberOrColor;
import kamel.unoengine.utils.Colors;
import kamel.unoengine.utils.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SimpleDeckGeneration implements DeckGenerationStrategy {
    private static int id = 1;
    List<Card> cards;

    @Override
    public List<Card> generate() {
        cards = new ArrayList<>(128);
        createZeroCards();
        createRestOfNumbersCards();
        createSkipCards();
        createDraw2Cards();
        createReverseCards();
        createWildCards();
        createWild4Cards();
        createWildPlayAgainCards();
        cards = shuffle();
        return cards;
    }

    @Override
    public void addUsedColors() {
        Game.addCardColor(Colors.BLUE);
        Game.addCardColor(Colors.RED);
        Game.addCardColor(Colors.ORANGE);
        Game.addCardColor(Colors.GREEN);
    }

    private void createZeroCards() {
        createSetsOfCards("0", new CardNoAction(), new PlayOverNumberOrColor(),1);
    }

    private void createRestOfNumbersCards() {
        for (int i = 1; i <= 9; i++) {
            createSetsOfCards(String.valueOf(i), new CardNoAction(), new PlayOverNumberOrColor(),2);
        }
    }

    private void createSkipCards() {
        createSetsOfCards(Constants.SKIP_CARD_LABEL, new CardSkip(), new PlayOverNumberOrColor(), 2);
    }

    private void createDraw2Cards() {
        createSetsOfCards(Constants.DRAW_TWO_CARD_LABEL, new CardDraw(2), new PlayOverNumberOrColor(), 2);
    }

    private void createReverseCards() {
        createSetsOfCards(Constants.REVERSE_CARD_LABEL, new CardReverse(), new PlayOverNumberOrColor(), 2);
    }

    private void createWildCards() {
        createSingleSetOfCards(Constants.WILD, new CardWild(), new PlayOverAll(), 4);
    }

    private void createWild4Cards() {
        createSingleSetOfCards(Constants.WILD4, new CardWild4(), new PlayOverAll(), 4);
    }

    private void createWildPlayAgainCards() {
        createSingleSetOfCards(Constants.WILD_PLAY_AGAIN, new CardWildPlayAgain(), new PlayOverAll(), 4);
    }

    private void createSetsOfCards(String label, CardActionStrategy action, CardPlayOverStrategy playOver, int repetition) {
        for (int i = 0; i < repetition; i++) {
            Card[] createdCards = new Card[] {
                    new StandardUnoCard(id++, label, Colors.BLUE),
                    new StandardUnoCard(id++, label, Colors.RED),
                    new StandardUnoCard(id++, label, Colors.ORANGE),
                    new StandardUnoCard(id++, label, Colors.GREEN),
            };
            for (Card createdCard : createdCards) {
                createdCard.setActionStrategy(action);
                createdCard.setPlayOverStrategy(playOver);
            }
            cards.addAll(Arrays.stream(createdCards).toList());
        }
    }

    private void createSingleSetOfCards(String label, CardActionStrategy action, CardPlayOverStrategy playOver, int repetition) {
        for (int i = 0; i < repetition; i++) {
            Card card = new StandardUnoCard(id++, label, Colors.BLUE);
            card.setActionStrategy(action);
            card.setPlayOverStrategy(playOver);
            cards.add(card);
        }
    }

    private List<Card> shuffle() {
        LinkedList<Card> shuffledCards = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            shuffledCards.clear();
            while (cards.size() > 0)
                shuffledCards.add(cards.remove((int) (Math.random() * (cards.size() - 1))));
            cards = new ArrayList<>(List.copyOf(shuffledCards));
        }
        return shuffledCards;
    }
}
