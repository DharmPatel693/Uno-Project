package ca.sheridancollege.project;

import java.util.ArrayList;

public class UNOPlayer extends Player {
    private ArrayList<UNOCard> hand;

    public UNOPlayer(String name) {
        super(name);
        hand = new ArrayList<>();
    }

    public void drawCard(UNOCard card) {
        hand.add(card);
    }

    public void playCard(UNOCard card) {
        hand.remove(card);
    }

    public ArrayList<UNOCard> getHand() {
        return hand;
    }

    @Override
    public void play() {
        if (!hand.isEmpty()) {
            UNOCard cardToPlay = hand.get(0);  // Play the first card in the hand
            hand.remove(cardToPlay);
            System.out.println(getPlayerID() + " played: " + cardToPlay);
        } else {
            System.out.println(getPlayerID() + " has no cards to play.");
        }
    }
}
