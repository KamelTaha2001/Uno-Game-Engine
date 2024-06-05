package kamel.unoengine.abstraction;

import kamel.unoengine.concretion.card.strategies.action.CardNoAction;
import kamel.unoengine.concretion.card.strategies.playover.PlayOverAll;
import kamel.unoengine.utils.Color;
import kamel.unoengine.utils.Colors;

public abstract class Card {
    private int id;
    private String label;
    private CardActionStrategy actionStrategy;
    private CardPlayOverStrategy playOverStrategy;
    private Color color;
    private int value = 0;

    public Card(int id, String label, Color color) {
        this.id = id;
        this.label = label;
        this.color = color;
        this.actionStrategy = new CardNoAction();
        this.playOverStrategy = new PlayOverAll();
    }

    // -------------------------- Getters and Setters --------------------------

    public int getId() { return id; }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setActionStrategy(CardActionStrategy action) { this.actionStrategy = action; }

    public void setPlayOverStrategy(CardPlayOverStrategy playOver) { this.playOverStrategy = playOver; }

    // -------------------------- Public Methods --------------------------

    public void performAction(Game game)
    {
        actionStrategy.performAction(game);
    }

    public boolean canBePlayedOver(Card card) { return playOverStrategy.canPlayOver(this, card); }

    // -------------------------- Overridden Methods --------------------------

    @Override
    public String toString() {
        return Colors.WHITE.color() + "(" + id + ") " + color.color() + label;
    }
}
