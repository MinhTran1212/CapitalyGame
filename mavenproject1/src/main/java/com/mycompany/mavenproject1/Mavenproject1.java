package com.mycompany.mavenproject1;

public class Mavenproject1 {

    public static void main(String[] args) {
        CapitalyGame game = new CapitalyGame();
        try {
            game.loadGame("game_data.txt");
            game.play();
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
}