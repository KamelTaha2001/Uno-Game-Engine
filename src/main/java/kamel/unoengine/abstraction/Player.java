package kamel.unoengine.abstraction;

import kamel.unoengine.concretion.comment.Comments;
import kamel.unoengine.concretion.rule.Rule;
import kamel.unoengine.utils.ConsoleUtility;
import kamel.unoengine.utils.Constants;
import java.util.*;

/**
 * This class represents a Uno player.
 * It might either be a real player or a bot.
 */
public abstract class Player {
    private Game game;
    private String name;
    private List<Card> cards;
    private int score;
    private Set<String> comments;
    private PlayTurnStrategy turnStrategy;
    private NumberedQuestionStrategy numberedQuestionStrategy;


    public Player(Game game, PlayTurnStrategy turnStrategy) {
        this.game = game;
        this.name = askForName();
        this.turnStrategy = turnStrategy;
        cards = new LinkedList<>();
        comments = new HashSet<>();
        game.joinGame(this);
    }

    // -------------------------- Abstract Methods --------------------------

    public abstract Card chooseCard();

    public abstract String askForName();

    // -------------------------- Getters and Setters --------------------------

    public String getName() { return name; }

    public int getScore() { return score; }

    public Game getGame() {
        return game;
    }

    public Iterator<Card> getCards() { return cards.iterator(); }

    public NumberedQuestionStrategy getNumberedQuestionStrategy() { return numberedQuestionStrategy; }

    public void setNumberedQuestionStrategy(NumberedQuestionStrategy numberedQuestionStrategy) { this.numberedQuestionStrategy = numberedQuestionStrategy; }

    // -------------------------- Public Methods --------------------------

    /**
     * Prompts the player to play the turn depending
     * on the strategy used for {@link #turnStrategy}.
     */
    public void playTurn() {
        turnStrategy.playTurn(getGame(), this);
    }

    /**
     * Adds a card to the cards in player's hand.
     * @param givenCard The card to add.
     */
    public void giveCard(Card givenCard) {
        cards.add(givenCard);
        uncomment(Comments.UNO.getCommentText());
    }

    /**
     * Adds a number of cards to the cards in player's hand.
     * @param givenCards Cards to add.
     */
    public void giveCards(List<Card> givenCards) {
        cards.addAll(givenCards);
        uncomment(Comments.UNO.getCommentText());
    }

    /**
     * Adds a comment to the comments the player said.
     * @param comment The comment to add.
     */
    public void comment(String comment) {
        comments.add(comment.toUpperCase());
    }

    /**
     * Removes a comment from the comments the player said.
     * Used when the need of the comment is done, like when
     * the player has already said uno, but after that he was given a
     * card, which implies he does not have a single card anymore.
     * @param comment The comment to remove.
     */
    public void uncomment(String comment) {
        comments.remove(comment.toUpperCase());
    }

    /**
     * Checks whether the player has recently commented
     * a specific comment.
     * @param comment The comment to check.
     * @return True if commented.
     */
    public boolean didComment(String comment) {
        for (String c : comments) {
            if (c.equalsIgnoreCase(comment))
                return true;
        }
        return false;
    }

    /**
     * Depending on the cards in player's hand,
     * taking into consideration the card on the table.
     * this method checks if the player can play any card
     * from the cards he is holding.
     * @return True if the player can play any of his cards.
     */
    public boolean canPlayAnyCards() {
        for (Card c : cards) {
            if (c.canBePlayedOver(game.getCardOnTable())){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a card from the player's hand using its id.
     * @param id The id of the card to search for.
     * @return The found card, or throws an exception if the
     * player does not have a card with this id.
     * @throws NullPointerException
     */
    public Card getCardById(int id) throws NullPointerException {
        List<Card> matchingCards =  cards.stream().filter(c -> c.getId() == id).toList();
        if (matchingCards.size() > 0)
            return matchingCards.get(0);
        throw new NullPointerException("You do not have a card with an id of (" + id + ")");
    }

    /**
     * @return The number of cards in player's hand.
     */
    public int getNumberOfCards() {
        return cards.size();
    }

    /**
     * Throws the card from player's hand.
     * Then it performs the action corresponding to the card.
     */
    public void discardCard() {
        Card card = chooseCard();
        cards.remove(card);
        game.throwCardOnTable(card);
        card.performAction(game);
    }

    /**
     * Asks the player to enter one or more comment from the available comments.
     * @param availableComments List of the accepted comments for this prompt,
     *                          sometimes you don't want the player to comment
     *                          "Draw" when he has already drawn a card.
     */
    public void promptForComment(List<String> availableComments) {
        String prompt = ConsoleUtility.promptToEnterComment(availableComments);
        while (!prompt.equalsIgnoreCase(Constants.END_COMMAND_PROMPT)) {
            boolean commentAccepted = false;
            for (String c : availableComments) {
                if (c.equalsIgnoreCase(prompt)) {
                    commentAccepted = true;
                    break;
                }
            }
            if (commentAccepted)
                comment(prompt);
            prompt = ConsoleUtility.promptToEnterComment(availableComments);
        }
    }

    /**
     * Check if a specific rule is satisfied, if yes
     * it applies the consequences of that rule.
     * @param rule The rule to check.
     * @return True if the rule is satisfied.
     */
    public boolean validateAndHandleRule(Rule rule) {
        if (getGame().validateRule(rule.getRuleName())) {
            getGame().handleRule(rule.getRuleName());
            return true;
        }
        return false;
    }

    /**
     * Removes all cards from player's hand and
     * adds them back to the bottom of the deck.
     */
    public void dropAllCards() {
        while (!cards.isEmpty())
            game.getDeck().addCardToBottom(cards.remove(0));
    }

    /**
     * Clears all comments the player has already commented.
     */
    public void removeAllComments() {
        comments.clear();
    }

    /**
     * This strategy decides how the player would handle
     * a numbered question, such as choosing a color after
     * playing a wild card.
     * It might differ between a real player or a bot. Or
     * you might want to punish the real player by adding
     * a rule that forces him to always choose the first color
     * when he plays a wild card.
     * @return The number of the chosen choice.
     */
    public int handleNumberedQuestion() { return numberedQuestionStrategy.handle(game); }

    // -------------------------- Overridden Methods --------------------------

    @Override
    public String toString() {
        return name;
    }
}
