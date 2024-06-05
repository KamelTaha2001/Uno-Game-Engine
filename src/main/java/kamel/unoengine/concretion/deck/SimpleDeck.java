package kamel.unoengine.concretion.deck;

import kamel.unoengine.abstraction.Deck;
import kamel.unoengine.concretion.deck.strategies.generation.SimpleDeckGeneration;

public class SimpleDeck extends Deck {
    public SimpleDeck() {
        super(new SimpleDeckGeneration());
    }
}
