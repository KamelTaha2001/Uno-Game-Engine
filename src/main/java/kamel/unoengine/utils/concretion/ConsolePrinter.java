package kamel.unoengine.utils.concretion;

import kamel.unoengine.utils.Color;
import kamel.unoengine.utils.Colors;
import kamel.unoengine.utils.abstraction.Printer;
import java.util.Scanner;

public class ConsolePrinter extends Printer {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.print(Colors.GREY.color() + message);
    }

    @Override
    public void print(String message, Color color) {
        System.out.print(color.color() + message + Colors.GREY.color());
    }

    @Override
    public void print(String message, Color color, Color backgroundColor) {
        System.out.print(color.color() + backgroundColor.color() + message + Colors.ANSI_RESET.color());
    }

    @Override
    public void println(String message) { System.out.println(Colors.GREY.color() + message); }

    @Override
    public void println(String message, Color color) {
        System.out.println(color.color() + message + Colors.GREY.color());
    }

    @Override
    public void println(String message, Color color, Color backgroundColor) {
        System.out.println(color.color() + backgroundColor.color() + message + Colors.ANSI_RESET.color());
    }

    @Override
    public String readInput() {
        return scanner.nextLine();
    }
}
