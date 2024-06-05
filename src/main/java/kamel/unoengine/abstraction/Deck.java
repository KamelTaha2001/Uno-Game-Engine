package kamel.unoengine.abstraction;
import java.util.List;

/**
 * This class represents the deck of cards used in the Uno game.
 * The deck is automatically generated on the construction time of the object.
 * The method {@link DeckGenerationStrategy#addUsedColors()} is called in the constructor.
 * It must be called to display the choosing color question properly, with the correct colors.
 */
public abstract class Deck {

    private List<Card> cards;
    private DeckGenerationStrategy generationStrategy;

    public Deck(DeckGenerationStrategy generationStrategy) {
        this.generationStrategy = generationStrategy;
        cards = generationStrategy.generate();
        generationStrategy.addUsedColors();
    }

    /**
     * Draws a single card from the top of the deck,
     * regardless strategy returned from {@link Game#getDrawStrategy()}.
     * @return The drawn card.
     */
    public Card drawSingleCard() {
        return cards.remove(cards.size() - 1);
    }

    /**
     * Adds a card to the bottom of the deck.
     * Adding a card to the bottom of the deck
     * represents throwing a card on the table.
     * Instead of creating a separate variable for
     * the current card on table, we can instead
     * check the most bottom card of the deck.
     * @param card The card to add.
     */
    public void addCardToBottom(Card card) {
        cards.add(0, card);
    }

    /**
     * A more English-like method for getting the
     * most bottom card of the deck.
     * @return The most bottom card of the deck,
     * which represents the card on the table.
     */
    public Card getCardOnTable() {
        return cards.get(0);
    }

    /**
     * Allows us to look at a number of cards inside the deck.
     * @param number The number of cards to return.
     * @param fromTop Whether to get these cards from the top
     *                or the bottom of the deck.
     * @return Cards returned from the deck.
     */
    public List<Card> peekCards(int number, boolean fromTop) {
        if (fromTop)
            return cards.subList(cards.size() - number, cards.size());
        else
            return cards.subList(0, number);
    }
}
