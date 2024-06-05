package kamel.unoengine.abstraction;

/**
 * This class uses the decorator pattern to allow
 * us to add multiple listeners to a specific event
 * of type {@link GameEvent}.
 */
public abstract class GameEventDecorator implements GameEvent {
    private GameEvent listener;

    public GameEventDecorator(GameEvent listener) {
        this.listener = listener;
    }

    public GameEvent getListener() {
        return listener;
    }
}
