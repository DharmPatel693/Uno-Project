package ca.sheridancollege.project;

public class UNOCard extends Card {
    public enum Color { RED, YELLOW, GREEN, BLUE, WILD }
    public enum Type { NUMBER, SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR }

    private Color color;
    private Type type;
    private int number;

    public UNOCard(Color color, Type type, int number) {
        this.color = color;
        this.type = type;
        this.number = number;
    }

    @Override
    public String toString() {
        if (type == Type.NUMBER) {
            return color + " " + number;
        } else {
            return color + " " + type;
        }
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }
}
