package kamel.unoengine.abstraction;

/**
 * This interface is used to bind a
 * specific event occurring in the
 * game.
 * Listeners can set the value of the
 * instance assigned in the {@link Game}
 * object using the appropriate setter method.
 * Multiple listeners can be set for the same
 * event by utilizing {@link GameEventDecorator}.
 */
public interface GameEvent {
    void onExecuted(Game game);
}
