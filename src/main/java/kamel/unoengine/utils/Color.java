package kamel.unoengine.utils;

public record Color(String color) {
    @Override
    public String toString() {
        return color + "*" + Colors.WHITE.color();
    }
}
