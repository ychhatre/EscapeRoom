/*
 * Problem 1: Escape Room
 *
 * V1.0
 * 10/10/2019
 * Copyright(c) 2019 PLTW to present. All rights reserved
 */

import java.util.Scanner;

/**
 * Create an escape room game where the player must navigate
 * to the other side of the screen in the fewest steps, while
 * avoiding obstacles and collecting prizes.
 */
public class EscapeRoom {

    // describe the game with brief welcome message
    // determine the size (length and width) a player must move to stay within the grid markings
    // Allow game commands:
    //    right, left, up, down: if you try to go off grid or bump into wall, score decreases
    //    jump over 1 space: you cannot jump over walls
    //    if you land on a trap, spring a trap to increase score: you must first check if there is a trap, if none exists, penalty
    //    pick up prize: score increases, if there is no prize, penalty
    //    help: display all possible commands
    //    end: reach the far right wall, score increase, game ends, if game ended without reaching far right wall, penalty
    //    replay: shows number of player steps and resets the board, you or another player can play the same board
    // Note that you must adjust the score with any method that returns a score
    // Optional: create a custom image for your player use the file player.png on disk

    /**** provided code:
     // set up the game
     boolean play = true;
     while (play)
     {
     // get user input and call game methods to play
     play = false;
     }
     */

    static int trapHit(int xVal, int yVal, int score, GameGUI game, Scanner jumpScanner) {
        System.out.println("Do you want to jump? (Input Y or N): ");
        String jumpCheck = jumpScanner.nextLine();
        if (jumpCheck.equalsIgnoreCase("y")) {
            score += game.springTrap(xVal, yVal);
            game.movePlayer(xVal * 2, yVal * 2);
        } else {
            game.movePlayer(xVal, yVal);
            if (game.isTrap(0, 0)) {
                score -= 5;
            }
            if (game.isTrap(60, 0) || game.isTrap(-60, 0) || game.isTrap(0, -60) || game.isTrap(0, 60)) {
                System.out.println("A TRAP IS NEARBY");
            }
        }

        System.out.println("score=" + score);
        System.out.println("steps=" + game.getSteps() + " ");
        return score;
    }

    public static void main(String[] args) {
        // welcome message
        System.out.println("Welcome to EscapeRoom!");
        System.out.println("Get to the other side of the room, avoiding walls and invisible traps,");
        System.out.println("pick up all the prizes.\n");
        System.out.println("Enter help or ? to get all the commands!");

        GameGUI game = new GameGUI();
        game.createBoard();

        int score = 0;

        String[] validCommands = {"right", "left", "up", "down", "r", "l", "u", "d",
                "jump", "jr", "jumpleft", "jl", "jumpup", "ju", "jumpdown", "jd",
                "pickup", "p", "quit", "q", "replay", "help", "?"};

        // set up game
        boolean play = true;
        Scanner jumpScanner = new Scanner(System.in);
        while (play) {
            System.out.println("Enter A Command: ");
            String input = UserInput.getValidInput(validCommands);
            switch (input) {
                case "r", "right" -> score = EscapeRoom.trapHit(60, 0, score, game, jumpScanner);
                case "l", "left" -> score = EscapeRoom.trapHit(-60, 0, score, game, jumpScanner);
                case "d", "down" -> score = EscapeRoom.trapHit(0, 60, score, game, jumpScanner);
                case "u", "up" -> score = EscapeRoom.trapHit(0, -60, score, game, jumpScanner);
                case "q", "quit" -> {
                    if (score >= 30) {
                        if (game.playerAtEnd()) {
                            System.out.println("Congrats you have won the game, it is automatically restarting for you!");
                            game.endGame();
                            game.replay();
                            play = false;
                        } else {
                            System.out.println("Sorry, however you are not at the edge yet, please make your way to the bottom right corner");
                        }
                    } else {
                        System.out.println("Sorry, you do not have enough points, please restart the game to try again if you believe you cannot achieve a score of 30");
                    }


                }
                case "?", "help", "h" -> {
                    System.out.println("Main Commands Are: ");
                    System.out.println("\"?\" or \"help\" to get commands!");
                    System.out.println("\"q\" or \"quit\" to quit the game when you think you have won!");
                    System.out.println("");
                    System.out.println("Player Commands Are: ");
                    System.out.println("\"u\" or \"up\" to move up!");
                    System.out.println("\"d\" or \"down\" to move down!");
                    System.out.println("\"r\" or \"right\" to move right!");
                    System.out.println("\"l\" or \"left\" to move left!");
                    System.out.println("\"p\" or \"pickup\" to pickup an item!");
                }
                case "p", "pickup" -> score += game.pickupPrize();
            }

        }

        System.out.println("score=" + score);
        System.out.println("steps=" + game.getSteps());
    }
}