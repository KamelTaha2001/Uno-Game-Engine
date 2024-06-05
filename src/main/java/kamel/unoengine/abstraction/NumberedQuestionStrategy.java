package kamel.unoengine.abstraction;

/**
 * Classes implementing this interface
 * define how to respond to a multiple
 * choice question.
 * It is useful to differentiate the
 * answering process between a real
 * player or a bot.
 * It might also be useful if we decide
 * to switch to regular UI instead of
 * using the console. Because then easily
 * we can create a new class implementing
 * this interface that supports UI for
 * answering multiple choice questions.
 */
public interface NumberedQuestionStrategy {
    int handle(Game game);
}
