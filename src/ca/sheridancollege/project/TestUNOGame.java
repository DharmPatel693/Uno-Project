package ca.sheridancollege.project;

import java.util.Scanner;

public class TestUNOGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the number of players
        System.out.println("Welcome to UNO!");
        System.out.print("Enter the number of players (1 to play against the computer): ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        // Create a new UNO game
        UNOGame game = new UNOGame("UNO");

        if (numPlayers == 1) {
            // Add a real player and a computer
            System.out.print("Enter your name: ");
            String playerName = scanner.nextLine();
            game.addPlayer(new UNOPlayer(playerName));  // Real player
            game.addPlayer(new UNOPlayer("Computer"));  // Computer player
        } else if (numPlayers > 1) {
            // Add real players
            for (int i = 1; i <= numPlayers; i++) {
                System.out.print("Enter name for player " + i + ": ");
                String playerName = scanner.nextLine();
                game.addPlayer(new UNOPlayer(playerName));
            }
        } else {
            System.out.println("Invalid number of players!");
            return;  // Exit the game
        }

        // Start the game
        game.startGame();

        // Play the game
        game.play();

        scanner.close();
    }
}
