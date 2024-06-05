package kamel.unoengine.concretion.deck.strategies.draw;

import kamel.unoengine.abstraction.Card;
import kamel.unoengine.abstraction.Deck;
import kamel.unoengine.abstraction.DrawStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DrawNumberOfCards implements DrawStrategy {
    private int numberOfCards;

    public DrawNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    @Override
    public List<Card> draw(Deck deck) {
        return IntStream.range(0, numberOfCards)
                .mapToObj(i -> deck.drawSingleCard())
                .collect(Collectors.toList());
    }
}
