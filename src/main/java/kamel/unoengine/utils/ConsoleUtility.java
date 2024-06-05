package kamel.unoengine.utils;

import kamel.unoengine.abstraction.Card;
import kamel.unoengine.abstraction.Player;
import kamel.unoengine.utils.abstraction.Printer;
import java.util.Iterator;
import java.util.List;

public class ConsoleUtility {
    private static Printer printer = Printer.getInstance();

    public static void printCards(Iterator<Card> cards) {
        printer.print("Your cards:\n");
        while (cards.hasNext()) {
            Card card = cards.next();
            printer.println(card.toString());
        }
    }

    public static int promptToChooseCard() throws NumberFormatException {
        printer.print("Enter the id of the card: ");
        return Integer.parseInt(printer.readInput());
    }

    public static String promptToEnterComment(List<String> comments) {
        printAvailableComments(comments);
        printer.print("Enter a comment (or enter " + Colors.WHITE.color() + "\"-q-\" " + Colors.GREY.color() + "if you don't have any comments): ");
        return printer.readInput();
    }

    public static void printAvailableComments(List<String> comments) {
        printer.print("Available comments: ");
        for (String c : comments)
            printer.print(c + " ", Colors.WHITE);
        printer.println("");
    }

    public static int promptToChooseColor(List<Color> colors) {
        for (int i = 0; i < colors.size(); i++)
            printer.println("(" + i + ") " + colors.get(i).toString());
        printer.println("");
        printer.print("Choose from the colors above (Enter the id): ");
        int index = Integer.parseInt(printer.readInput());
        while (index < 0 || index >= colors.size()) {
            printer.print("Wrong id, please choose from the colors above (Enter the id): ");
            index = Integer.parseInt(printer.readInput());
        }
        return index;
    }

    public static void printResults(List<Player> players) {
        printer.println("\n\n\n");
        printer.println("---------------------------------------- Results ----------------------------------------",
                Colors.WHITE, Colors.RED_BACKGROUND);
        printer.println("");
        for (int i = 0; i < players.size(); i++) {
            Color textColor = (i == 0) ? Colors.GREEN : Colors.WHITE;
            printer.println((i + 1) + "- " + players.get(i).getName(), textColor);
            printer.println("");
        }
    }

    public static String promptForName() {
        printer.print("Enter player name: ");
        return printer.readInput();
    }

    public static void printTurnSeparator(String currentPlayerName, Card cardOnTable) {
        printer.println("\n\n");
        printer.println("------------------------------", Colors.BLACK, Colors.BLUE_BACKGROUND);
        printer.print(matchWidthWith(currentPlayerName, "------------------------------"), Colors.BLACK, Colors.BLUE_BACKGROUND);
        printer.println("\tCard on table: " + cardOnTable.toString()
                ,Colors.GREY, Colors.ANSI_RESET);
        printer.println("------------------------------", Colors.BLACK, Colors.BLUE_BACKGROUND);
        printer.println("");
    }

    private static String matchWidthWith(String toBeStretched, String toBeMatched) {
        int spaces = toBeMatched.length() - toBeStretched.length();
        int halfSpaces = spaces % 2 == 0 ? spaces / 2 : spaces / 2 + 1;
        StringBuilder sb = new StringBuilder();
        sb.append(" ".repeat(Math.max(0, halfSpaces)));
        sb.append(toBeStretched);
        sb.append(" ".repeat(Math.max(0, spaces / 2)));
        return sb.toString();
    }

    public static int promptForNumberOfRealPlayers() throws NumberFormatException {
        printer.print("Enter number of real players: ");
        return Integer.parseInt(printer.readInput());
    }

    public static int promptForNumberOfBots() {
        printer.print("Enter number of bots: ");
        return Integer.parseInt(printer.readInput());
    }

    public static void printWithBackground(String message, Color background) {
        printer.println("");
        printer.println(message, Colors.WHITE, background);
        printer.println("");
    }
}
