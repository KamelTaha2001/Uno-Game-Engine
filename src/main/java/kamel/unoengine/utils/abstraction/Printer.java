package kamel.unoengine.utils.abstraction;

import kamel.unoengine.utils.Color;
import kamel.unoengine.utils.concretion.ConsolePrinter;

/**
 * Provides helper functions to print colorful text.
 * And to read input from the user.
 */
public abstract class Printer {
    private static Printer printer = null;

    public static Printer getInstance() {
        if (printer == null)
            printer = new ConsolePrinter();
        return printer;
    }

    public abstract void print(String message);

    public abstract void print(String message, Color color);

    public abstract void print(String message, Color color, Color backgroundColor);

    public abstract void println(String message);

    public abstract void println(String message, Color color);

    public abstract void println(String message, Color color, Color backgroundColor);

    public abstract String readInput();
}
