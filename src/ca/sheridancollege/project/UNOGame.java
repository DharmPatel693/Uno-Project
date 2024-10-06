package ca.sheridancollege.project;

import java.util.Scanner;

public class UNOGame extends Game {
    private int currentPlayerIndex;
    private UNOCard currentCard;
    private GroupOfCards deck;

    public UNOGame(String gameName) {
        super(gameName);  // Call the superclass constructor
        currentPlayerIndex = 0;
        deck = new GroupOfCards(108);  // Create the UNO deck with 108 cards
    }

    // Start the game by dealing 7 cards to each player and drawing the first card
    public void startGame() {
        for (Player player : getPlayers()) {
            if (player instanceof UNOPlayer) {
                UNOPlayer unoPlayer = (UNOPlayer) player;
                for (int i = 0; i < 7; i++) {
                    unoPlayer.drawCard(deck.draw());
                }
            }
        }

        // Set the first card in play
        currentCard = deck.draw();
        System.out.println("Starting card: " + currentCard);
    }

    @Override
    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean gameWon = false;

        while (!gameWon) {
            UNOPlayer currentPlayer = (UNOPlayer) getPlayers().get(currentPlayerIndex);
            System.out.println(currentPlayer.getPlayerID() + "'s turn!");
            System.out.println("Your hand: " + currentPlayer.getHand());

            if (currentPlayer.getPlayerID().equals("Computer")) {
                // Computer plays the first card
                UNOCard cardToPlay = currentPlayer.getHand().get(0);
                currentPlayer.playCard(cardToPlay);
                System.out.println("Computer played: " + cardToPlay);
                currentCard = cardToPlay;
            } else {
                // Real player selects a card
                System.out.println("Current card in play: " + currentCard);
                System.out.print("Choose a card to play by entering the index (0 for first card): ");
                int cardIndex = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                if (cardIndex >= 0 && cardIndex < currentPlayer.getHand().size()) {
                    UNOCard cardToPlay = currentPlayer.getHand().get(cardIndex);
                    currentPlayer.playCard(cardToPlay);
                    System.out.println(currentPlayer.getPlayerID() + " played: " + cardToPlay);
                    currentCard = cardToPlay;
                } else {
                    System.out.println("Invalid choice. Drawing a card...");
                    currentPlayer.drawCard(deck.draw());
                }
            }

            // Check for a winner
            if (currentPlayer.getHand().isEmpty()) {
                System.out.println(currentPlayer.getPlayerID() + " wins!");
                gameWon = true;
            } else {
                nextPlayer();
            }
        }

        scanner.close();  // Close the scanner once the game is finished
    }

    // Move to the next player
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size();
    }

    // Implement the declareWinner method from the Game class
    @Override
    public void declareWinner() {
        for (Player player : getPlayers()) {
            if (player instanceof UNOPlayer) {
                UNOPlayer unoPlayer = (UNOPlayer) player;
                if (unoPlayer.getHand().isEmpty()) {
                    System.out.println(unoPlayer.getPlayerID() + " is the winner!");
                    return;  // Exit after declaring the winner
                }
            }
        }
    }
}
