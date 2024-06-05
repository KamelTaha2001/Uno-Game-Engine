package kamel.unoengine.abstraction;

import kamel.unoengine.concretion.deck.SimpleDeck;
import kamel.unoengine.concretion.rule.Rule;
import kamel.unoengine.utils.Color;
import java.nio.channels.AlreadyConnectedException;
import java.util.*;

public abstract class Game {
    private Deck deck;
    private List<Player> players;
    private static final List<Color> usedCardColors = new ArrayList<>(4);
    private int turn = 0;
    private int minPlayers;
    private int maxPlayers;
    private int initialCards;
    private CardDiscardStrategy discardStrategy;
    private DrawStrategy drawStrategy;
    private PlayDirection playDirection;
    private HashMap<String, Rule> rules;
    private int turnsPlayed = 0;
    private GameEvent onCardThrown = null;
    private GameEvent onDraw = null;
    private GameEvent onColorChanged = null;
    private GameEvent onTurnStarted = null;
    private GameEvent onTurnFinished = null;
    private int freezeCount = 0;

    public enum PlayDirection {
        CLOCKWISE, COUNTER_CLOCKWISE
    }

    public Game(Deck deck, CardDiscardStrategy discardStrategy, DrawStrategy drawStrategy) {
        this.deck = deck;
        players = new ArrayList<>(minPlayers);
        minPlayers = 2;
        maxPlayers = 8;
        initialCards = 7;
        this.discardStrategy = discardStrategy;
        this.drawStrategy = drawStrategy;
        playDirection = PlayDirection.CLOCKWISE;
        rules = new HashMap<>();
    }

    // -------------------------- Abstract Methods --------------------------

    public abstract void play();

    // -------------------------- Getters and Setters --------------------------

    public Deck getDeck() {
        return deck;
    }

    public static List<Color> getUsedCardColors() { return usedCardColors; }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getInitialCards() {
        return initialCards;
    }

    public void setInitialCards(int initialCards) {
        this.initialCards = initialCards;
    }

    public CardDiscardStrategy getDiscardStrategy() { return discardStrategy; }

    public void setDiscardStrategy(CardDiscardStrategy discardStrategy) {
        this.discardStrategy = discardStrategy;
    }

    public DrawStrategy getDrawStrategy() { return drawStrategy; }

    public void setDrawStrategy(DrawStrategy drawStrategy) { this.drawStrategy = drawStrategy; }

    public PlayDirection getPlayDirection() {
        return playDirection;
    }

    public void setPlayDirection(PlayDirection playDirection) {
        this.playDirection = playDirection;
    }

    public int getTurnsPlayed() { return turnsPlayed; }

    public void setOnCardThrown(GameEvent onCardThrown) {
        this.onCardThrown = onCardThrown;
    }

    public void setOnDraw(GameEvent onDraw) {
        this.onDraw = onDraw;
    }

    public void setOnColorChanged(GameEvent onColorChanged) {
        this.onColorChanged = onColorChanged;
    }

    public void setOnTurnStarted(GameEvent onTurnStarted) {
        this.onTurnStarted = onTurnStarted;
    }

    public void setOnTurnFinished(GameEvent onTurnFinished) { this.onTurnFinished = onTurnFinished; }


    // -------------------------- Public Methods --------------------------

    public void joinGame(Player player) {
        if (players.contains(player))
            throw new AlreadyConnectedException();
        players.add(player);
    }

    /**
     * Must be called for every card color used.
     * Without adding the used color, the behavior may not be expected.
     * @param color The color to add.
     */
    public static void addCardColor(Color color) {
        usedCardColors.add(color);
    }

    /**
     * Used to remove a color from the used colors list.
     * Adding a color that is not used may lead to an unexpected behavior.
     * @param color The color to remove.
     */
    public static void removeCardColor(Color color) {
        usedCardColors.remove(color);
    }

    /**
     * Players don't have cards initially, this method must be called to
     * deal cards to all players.
     */
    public void dealCards() {
        for (int i = 0; i < initialCards; i++) {
            for (Player player : players) {
                player.giveCard(drawSingleCard());
            }
        }
    }

    /**
     * Draws card/s from the deck.
     * The number of drawn cards and the way of drawing them
     * depends on the strategy used for {@link #drawStrategy}.
     * This method is responsible for triggering {@link #onDraw}.
     * @return
     */
    public List<Card> draw() {
        List<Card> drawnCards = drawStrategy.draw(deck);
        callIfNotNull(onDraw);
        return drawnCards;
    }

    /**
     * Moves the turn to the next player.
     * It might move clockwise or counter-clockwise depending on
     * the return value of {@link #getPlayDirection()}.
     * This method is responsible for triggering {@link #onTurnStarted}.
     */
    public void nextPlayerTurn() {
        nextPlayerTurn(1, playDirection);
    }

    /**
     * Moves the turn to the next player by a number of steps.
     * It might move clockwise or counter-clockwise depending on
     * the return value of {@link #getPlayDirection()}.
     * This method is responsible for triggering {@link #onTurnStarted}.
     * @param steps Number of steps to move turn.
     */
    public void nextPlayerTurn(int steps) {
        nextPlayerTurn(steps, playDirection);
    }

    /**
     * Moves the turn to the next player by a number of steps in a given direction.
     * This method is responsible for triggering {@link #onTurnStarted}.
     * @param steps Number of steps to move turn.
     * @param direction The direction to move for.
     */
    public void nextPlayerTurn(int steps, PlayDirection direction) {
        if (freezeCount == 0)
            turn = getNextTurn(steps, direction);
        else
            --freezeCount;
        callIfNotNull(onTurnStarted);
    }

    /**
     * @param steps Number of steps to move turn.
     * @param direction The direction to move for.
     * @return Gets the index of the player that corresponds the turn.
     */
    public int getNextTurn(int steps, PlayDirection direction) {
        int factor = direction == PlayDirection.CLOCKWISE ? 1 : -1;
        int result = turn;
        for (int i = 0; i < steps; i++) {
            result += factor;
            if (result == players.size())
                result = 0;
            if (result == -1)
                result = players.size() - 1;
        }
        return result;
    }

    /**
     * Freezes the turn for the number of times passed.
     * {@link #nextPlayerTurn()} methods will not move
     * to the next turn, but the must be called to
     * decrement the number of times of freeze.
     * @param count How many turns to freeze.
     */
    public void freezeTurnFor(int count) {
        freezeCount = count;
    }

    /**
     * Prompts the player to play turn.
     * This method is responsible for triggering {@link #onColorChanged}.
     * It's also responsible for triggering {@link #onTurnFinished}.
     */
    public void askPlayerToPlayTurn() {
        if (turnsPlayed > 0 && didColorChange())
            callIfNotNull(onColorChanged);
        getCurrentPlayer().playTurn();
        callIfNotNull(onTurnFinished);
        turnsPlayed++;
    }

    /**
     *
     * @return The player who is playing turn now.
     * @throws IndexOutOfBoundsException
     */
    public Player getCurrentPlayer() throws IndexOutOfBoundsException {
        return players.get(turn);
    }

    /**
     * @return The directly next player in the current play direction.
     */
    public Player getNextPlayer() {
        return players.get(getNextTurn(1, playDirection));
    }

    /**
     * @param comparator Used to sort players.
     * @return Players sorted using the passed comparator.
     */
    public List<Player> getSortedPlayers(Comparator<Player> comparator) {
        List<Player> sortedPlayers = new ArrayList<>(List.copyOf(players));
        sortedPlayers.sort(comparator);
        return sortedPlayers;
    }

    /**
     * The card on the table is the same card that is at the bottom of the deck.
     * @return The last card thrown on table.
     */
    public Card getCardOnTable() {
        return deck.getCardOnTable();
    }

    /**
     * Handles the card to be thrown by adding it to the bottom of the deck.
     * This method is responsible for triggering {@link #onCardThrown}.
     * @param card Thrown card.
     */
    public void throwCardOnTable(Card card) {
        deck.addCardToBottom(card);
        callIfNotNull(onCardThrown);
    }

    /**
     * @return Draws a single card from the deck and returns it.
     */
    public Card drawSingleCard() {
        return deck.drawSingleCard();
    }

    /**
     * Must be called to add any new {@link Rule} to the Uno game.
     * @param rule The rule to be added.
     */
    public void addGameRule(Rule rule) { rules.put(rule.getRuleName().toUpperCase(), rule); }

    /**
     * Called to remove an existing {@link Rule} from the Uno game.
     * @param ruleName The name of the rule to be removed.
     */
    public Rule removeGameRule(String ruleName) { return rules.remove(ruleName); }

    /**
     * @param ruleName The name of the rule to search for.
     * @return The rule represented by the passed name, if exists in the game.
     * @throws IllegalArgumentException
     */
    public Rule getGameRule(String ruleName) throws IllegalArgumentException {
        Rule rule = rules.get(ruleName.toUpperCase());
        if (rule == null)
            throw new IllegalArgumentException("No such rule with that name, or the rule name exists but is mapped to a null rule.");
        return rule;
    }

    /**
     * Checks if a specific rule condition is satisfied.
     * @param ruleName The name of the rule to be checked.
     * @return True if satisfied.
     * @throws IllegalArgumentException
     */
    public boolean validateRule(String ruleName) throws IllegalArgumentException {
        return getGameRule(ruleName.toUpperCase()).getRuleValidator().check(this);
    }

    /**
     * Handles a specific rule and applies its consequences.
     * @param ruleName The name of the rule.
     * @throws IllegalArgumentException
     */
    public void handleRule(String ruleName) throws IllegalArgumentException {
        getGameRule(ruleName.toUpperCase()).getHandler().apply(this);
    }

    /**
     * Must be called before restarting the game again
     * to reinitialize the game conditions.
     */
    public void resetGameConditions() {
        for (Player player : players) {
            player.dropAllCards();
            player.removeAllComments();
            deck = new SimpleDeck();
            turn = 0;
            turnsPlayed = 0;
            playDirection = PlayDirection.CLOCKWISE;
        }
    }

    // -------------------------- Private Methods --------------------------

    private void callIfNotNull(GameEvent evt) {
        if (evt == null)
            return;
        evt.onExecuted(this);
    }

    /**
     * Returns true if the color of the card currently put on the table
     * is different from the color of the previous card.
     * @return True if the color has changed.
     */
    private boolean didColorChange() {
        List<Card> lastTwoCards = deck.peekCards(2, false);
        return !lastTwoCards.get(0).getColor().color().equals(lastTwoCards.get(1).getColor().color());
    }
}
