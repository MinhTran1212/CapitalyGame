package com.mycompany.mavenproject1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CapitalyGame {
    private List<Field> board = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private List<Integer> diceRolls = new ArrayList<>();

    // 1. Hàm đọc file cơ bản nhất
    public void loadGame(String fileName) throws FileNotFoundException, InvalidInputException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        Scanner scanner = new Scanner(file);

        // Đọc các ô trên bàn cờ
        int numFields = scanner.nextInt();
        for (int i = 0; i < numFields; i++) {
            String type = scanner.next().toUpperCase();
            if (type.equals("PROPERTY")) {
                board.add(new PropertyField());
            } else if (type.equals("SERVICE")) {
                board.add(new ServiceField(scanner.nextInt()));
            } else if (type.equals("LUCKY")) {
                board.add(new LuckyField(scanner.nextInt()));
            } else {
                scanner.close();
                throw new InvalidInputException("Type invalid: " + type);
            }
        }

        // Đọc danh sách người chơi
        int numPlayers = scanner.nextInt();
        if (numPlayers < 2) {
            scanner.close();
            throw new InvalidInputException("Need at least 2 players!");
        }

        for (int i = 0; i < numPlayers; i++) {
            String name = scanner.next();
            String strategy = scanner.next().toUpperCase();
            if (strategy.equals("GREEDY")) {
                players.add(new GreedyPlayer(name));
            } else if (strategy.equals("CAREFUL")) {
                players.add(new CarefulPlayer(name));
            } else if (strategy.equals("TACTICAL")) {
                players.add(new TacticalPlayer(name));
            } else {
                scanner.close();
                throw new InvalidInputException("Strategy Invalid: " + strategy);
            }
        }

        // Đọc các lượt xúc xắc
        while (scanner.hasNextInt()) {
            int roll = scanner.nextInt();
            if (roll < 1 || roll > 6) {
                scanner.close();
                throw new InvalidInputException("Dice need to be from 1 to 6: " + roll);
            }
            diceRolls.add(roll);
        }

        scanner.close();
    }

    // 2. Vòng lặp chạy game
    public void play() {
        int rollIndex = 0;
        int activePlayers = players.size();

        while (rollIndex < diceRolls.size() && activePlayers > 1) {
            for (Player player : players) {
                if (player.getBankrupt()) {
                    continue; // Bỏ qua người đã phá sản
                }
                if (rollIndex >= diceRolls.size()) {
                    break; // Hết xúc xắc thì dừng
                }

                // Lấy xúc xắc và di chuyển
                int steps = diceRolls.get(rollIndex++);
                player.move(steps, board.size());

                // Bước vào ô
                Field currentField = board.get(player.getPosition());
                currentField.stepOn(player);

                // Kiểm tra nếu vừa phá sản
                if (player.getBankrupt()) {
                    activePlayers--;
                    if (activePlayers <= 1) {
                        break;
                    }
                }
            }
        }

        // In kết quả người thắng
        printWinner();
    }

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
            System.out.println("Winner: " + winner.getName());
            System.out.println("Money: " + winner.getBalance());
            System.out.println("Number of field own: " + winner.getFields().size());
        } else {
            System.out.println("Every players bankrupted!");
        }
    }
}