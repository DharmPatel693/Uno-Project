package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A concrete class that represents a group of UNO cards for the game.
 * @author dancye
 */
public class GroupOfCards {
    private ArrayList<UNOCard> cards;  // the group of cards
    private int size;  // the size of the deck

    public GroupOfCards(int givenSize) {
        size = givenSize;
        cards = new ArrayList<>(size);
        createDeck();
        shuffle();
    }

    // Create the deck of UNO cards
    public void createDeck() {
        for (UNOCard.Color color : UNOCard.Color.values()) {
            if (color != UNOCard.Color.WILD) {
                // Add number cards (0-9)
                for (int i = 0; i <= 9; i++) {
                    cards.add(new UNOCard(color, UNOCard.Type.NUMBER, i));
                    if (i != 0) cards.add(new UNOCard(color, UNOCard.Type.NUMBER, i));  // Two of each number except 0
                }
                // Add action cards
                cards.add(new UNOCard(color, UNOCard.Type.SKIP, -1));
                cards.add(new UNOCard(color, UNOCard.Type.SKIP, -1));
                cards.add(new UNOCard(color, UNOCard.Type.REVERSE, -1));
                cards.add(new UNOCard(color, UNOCard.Type.REVERSE, -1));
                cards.add(new UNOCard(color, UNOCard.Type.DRAW_TWO, -1));
                cards.add(new UNOCard(color, UNOCard.Type.DRAW_TWO, -1));
            }
        }
        // Add wild cards
        for (int i = 0; i < 4; i++) {
            cards.add(new UNOCard(UNOCard.Color.WILD, UNOCard.Type.WILD, -1));
            cards.add(new UNOCard(UNOCard.Color.WILD, UNOCard.Type.WILD_DRAW_FOUR, -1));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public UNOCard draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("No more cards in the deck.");
        }
        return cards.remove(0);
    }

    public ArrayList<UNOCard> showCards() {
        return cards;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int givenSize) {
        size = givenSize;
    }
}
