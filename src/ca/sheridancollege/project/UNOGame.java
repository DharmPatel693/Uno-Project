package ca.sheridancollege.project;

import java.util.Scanner;

public class UNOGame extends Game {
    private int currentPlayerIndex;
    private UNOCard currentCard;
    private GroupOfCards deck;

    public UNOGame(String gameName) {
        super(gameName);
        currentPlayerIndex = 0;
        deck = new GroupOfCards(108);
    }

    public void displayGameRules() {
        System.out.println("\nWelcome to UNO!");
        System.out.println("Game Rules:");
        System.out.println("1. Match cards by color or number.");
        System.out.println("2. Play special cards like Skip, Reverse, and Draw Two when applicable.");
        System.out.println("3. Use Wild and Wild Draw Four to change the game.");
        System.out.println("4. First player to play all cards wins!");
        System.out.println("5. To draw a card, enter 0.\n");
    }

    public void startGame() {
        displayGameRules();
        for (Player player : getPlayers()) {
            if (player instanceof UNOPlayer) {
                UNOPlayer unoPlayer = (UNOPlayer) player;
                for (int i = 0; i < 7; i++) {
                    unoPlayer.drawCard(deck.draw());
                }
            }
        }
        currentCard = deck.draw();
        System.out.println("Starting card: " + currentCard + "\n");
    }

    @Override
public void play() {
    Scanner scanner = new Scanner(System.in);
    boolean gameWon = false;

    while (!gameWon) {
        UNOPlayer currentPlayer = (UNOPlayer) getPlayers().get(currentPlayerIndex);
        System.out.println("\n" + currentPlayer.getPlayerID() + "'s turn!");
        displayPlayerHand(currentPlayer);
        System.out.println("Current card in play: " + currentCard);

        if (currentPlayer.getPlayerID().equals("Computer")) {
            // Computer's turn logic
            boolean validMove = false;
            for (UNOCard card : currentPlayer.getHand()) {
                if (isValidPlay(card, currentCard)) {
                    currentPlayer.playCard(card);
                    System.out.println("Computer played: " + card);
                    currentCard = card;
                    handleSpecialCard(card);
                    validMove = true;
                    break;
                }
            }
            if (!validMove) {
                System.out.println("Computer has no valid move. Drawing a card...");
                currentPlayer.drawCard(deck.draw());
            }
        } else {
            // Real player's turn
            boolean validMove = false;

            while (!validMove) {
                System.out.print("Choose a card to play by entering the index (0 to draw a card, or type 'exit' to quit): ");
                String input = scanner.nextLine();  // Read input as a string

                // Check for exit condition
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("You have chosen to exit the game. Goodbye!");
                    gameWon = true;
                    break;
                }

                int cardIndex = -1;
                try {
                    cardIndex = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number or type 'exit' to quit.");
                    continue;
                }

                if (cardIndex == 0) {
                    System.out.println("You chose to draw a card.");
                    currentPlayer.drawCard(deck.draw());
                    validMove = true;
                } else if (cardIndex > 0 && cardIndex <= currentPlayer.getHand().size()) {
                    UNOCard cardToPlay = currentPlayer.getHand().get(cardIndex - 1);
                    if (isValidPlay(cardToPlay, currentCard)) {
                        currentPlayer.playCard(cardToPlay);
                        System.out.println(currentPlayer.getPlayerID() + " played: " + cardToPlay);
                        currentCard = cardToPlay;
                        handleSpecialCard(cardToPlay);
                        validMove = true;
                    } else {
                        System.out.println("Invalid move! Please select a valid card.\n");
                    }
                } else {
                    System.out.println("Invalid choice! Please enter a valid index or type 'exit' to quit.\n");
                }
            }
        }

        // Check for a winner
        if (currentPlayer.getHand().isEmpty()) {
            System.out.println(currentPlayer.getPlayerID() + " wins!");
            gameWon = true;
        } else if (!gameWon) {
            nextPlayer();
        }
    }

    scanner.close();
}


    // Display the player's hand with indexed positions
    private void displayPlayerHand(UNOPlayer currentPlayer) {
        System.out.print("Your hand: [");
        for (int i = 0; i < currentPlayer.getHand().size(); i++) {
            UNOCard card = currentPlayer.getHand().get(i);
            System.out.print("(" + (i + 1) + ")" + card);
            if (i < currentPlayer.getHand().size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size();
    }

    public void handleSpecialCard(UNOCard card) {
        switch (card.getType()) {
            case SKIP:
                System.out.println("Next player is skipped!");
                nextPlayer();
                break;
            case REVERSE:
                System.out.println("Play direction reversed!");
                // Add reversal logic if necessary
                break;
            case DRAW_TWO:
                System.out.println("Next player draws two cards!");
                nextPlayer();
                UNOPlayer nextPlayer = (UNOPlayer) getPlayers().get(currentPlayerIndex);
                nextPlayer.drawCard(deck.draw());
                nextPlayer.drawCard(deck.draw());
                break;
            case WILD:
                System.out.println("Wild card played! Choose a new color.");
                // Add logic for choosing a color
                break;
            case WILD_DRAW_FOUR:
                System.out.println("Wild Draw Four played! Next player draws four cards.");
                nextPlayer();
                UNOPlayer nextPlayerDrawFour = (UNOPlayer) getPlayers().get(currentPlayerIndex);
                for (int i = 0; i < 4; i++) {
                    nextPlayerDrawFour.drawCard(deck.draw());
                }
                // Add color-changing logic if needed
                break;
            default:
                break;
        }
    }

    public boolean isValidPlay(UNOCard playedCard, UNOCard topCardOnPile) {
    // If the top card is a WILD or WILD_DRAW_FOUR, allow any card to be played
    if (topCardOnPile.getType() == UNOCard.Type.WILD || topCardOnPile.getType() == UNOCard.Type.WILD_DRAW_FOUR) {
        return true;
    }

    // Check if the colors match
    if (playedCard.getColor().equals(topCardOnPile.getColor())) {
        return true;
    }

    // Check if both are number cards and their numbers match
    if (playedCard.getType() == UNOCard.Type.NUMBER && topCardOnPile.getType() == UNOCard.Type.NUMBER) {
        return playedCard.getNumber() == topCardOnPile.getNumber();
    }

    // Check if the types match (for special cards)
    if (playedCard.getType() == topCardOnPile.getType()) {
        return true;
    }

    // Allow WILD and WILD_DRAW_FOUR to be played at any time
    if (playedCard.getType() == UNOCard.Type.WILD || playedCard.getType() == UNOCard.Type.WILD_DRAW_FOUR) {
        return true;
    }

    return false;  // If none of the above conditions are met, the play is invalid
}



    @Override
    public void declareWinner() {
        for (Player player : getPlayers()) {
            if (player instanceof UNOPlayer) {
                UNOPlayer unoPlayer = (UNOPlayer) player;
                if (unoPlayer.getHand().isEmpty()) {
                    System.out.println(unoPlayer.getPlayerID() + " is the winner!");
                    return;
                }
            }
        }
    }
}
