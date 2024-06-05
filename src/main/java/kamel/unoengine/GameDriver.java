package kamel.unoengine;

import kamel.unoengine.abstraction.Game;
import kamel.unoengine.concretion.game.OptionalDrawUno;

public class GameDriver {
    public static void main(String[] args) {
        Game standard = new OptionalDrawUno();
        standard.play();
    }
}