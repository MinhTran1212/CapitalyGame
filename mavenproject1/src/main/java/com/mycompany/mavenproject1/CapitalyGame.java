package com.mycompany.mavenproject1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Game controller responsible for reading game data, executing turns,
 * evaluating player states, and reporting the final winner.
 */
public class CapitalyGame {
    private List<Field> board;
    private List<Player> players;
    private List<Integer> diceRolls;

    /**
     * Constructs a CapitalyGame instance initializing empty board and player lists.
     */
    public CapitalyGame() {
        this.board = new ArrayList<>();
        this.players = new ArrayList<>();
        this.diceRolls = new ArrayList<>();
    }

    /**
     * Reads and parses game configuration and dice rolls from a data file.
     *
     * @param fileName the relative or absolute path to the configuration file
     * @throws FileNotFoundException if the configuration file is not found
     * @throws InvalidInputException if field types, player count, or values are invalid
     */
    public void loadGame(String fileName) throws FileNotFoundException, InvalidInputException {
        File file = new File(fileName);
        try (Scanner sc = new Scanner(file)) {
            if (!sc.hasNextInt()) {
                throw new InvalidInputException("Missing board size.");
            }
            int boardSize = sc.nextInt();
            if (boardSize <= 0) {
                throw new InvalidInputException("Board size must be positive.");
            }

            for (int i = 0; i < boardSize; i++) {
                if (!sc.hasNext()) {
                    throw new InvalidInputException("Incomplete board field data.");
                }
                String type = sc.next().toUpperCase();
                switch (type) {
                    case "PROPERTY":
                        board.add(new PropertyField());
                        break;
                    case "SERVICE":
                        if (!sc.hasNextInt()) {
                            throw new InvalidInputException("Missing service fee.");
                        }
                        board.add(new ServiceField(sc.nextInt()));
                        break;
                    case "LUCKY":
                        if (!sc.hasNextInt()) {
                            throw new InvalidInputException("Missing lucky reward.");
                        }
                        board.add(new LuckyField(sc.nextInt()));
                        break;
                    default:
                        throw new InvalidInputException("Unknown field type: " + type);
                }
            }

            if (!sc.hasNextInt()) {
                throw new InvalidInputException("Missing player count.");
            }
            int playerCount = sc.nextInt();
            if (playerCount < 2) {
                throw new InvalidInputException("There must be at least 2 players.");
            }

            for (int i = 0; i < playerCount; i++) {
                if (!sc.hasNext()) {
                    throw new InvalidInputException("Incomplete player entry.");
                }
                String name = sc.next();
                String strategy = sc.next().toUpperCase();
                switch (strategy) {
                    case "GREEDY":
                        players.add(new GreedyPlayer(name));
                        break;
                    case "CAREFUL":
                        players.add(new CarefulPlayer(name));
                        break;
                    case "TACTICAL":
                        players.add(new TacticalPlayer(name));
                        break;
                    default:
                        throw new InvalidInputException("Unknown strategy: " + strategy);
                }
            }

            while (sc.hasNextInt()) {
                diceRolls.add(sc.nextInt());
            }
        }
    }

    /**
     * Executes the main simulation loop advancing active players according to dice rolls,
     * triggering field landing effects, and tracking eliminations.
     */
    public void play() {
        int diceIndex = 0;
        while (diceIndex < diceRolls.size()) {
            int activePlayers = 0;
            for (Player p : players) {
                if (!p.getBankrupt()) {
                    activePlayers++;
                }
            }
            if (activePlayers <= 1) {
                break;
            }

            for (Player player : players) {
                if (player.getBankrupt()) {
                    continue;
                }
                if (diceIndex >= diceRolls.size()) {
                    break;
                }

                int roll = diceRolls.get(diceIndex++);
                player.move(roll, board.size());
                Field currentField = board.get(player.getPosition());
                currentField.stepOn(player);
            }
        }
        printWinner();
    }

    /**
     * Evaluates solvent players to identify and print the winning player to console.
     */
    private void printWinner() {
        Player winner = null;
        for (Player p : players) {
            if (!p.getBankrupt()) {
                if (winner == null || p.getBalance() > winner.getBalance()) {
                    winner = p;
                }
            }
        }

        if (winner != null) {
            System.out.println("Winner: " + winner.getName() + " with balance " + winner.getBalance() + " and " + winner.getFields().size() + " properties.");
        } else {
            System.out.println("All players went bankrupt!");
        }
    }
}