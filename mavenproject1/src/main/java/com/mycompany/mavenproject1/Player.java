package com.mycompany.mavenproject1;

import java.util.ArrayList;

/**
 * Abstract representation of a player in the Capitaly game.
 * Maintains financial balance, owned properties, board position, and bankruptcy state.
 */
public abstract class Player {
    private int balance;
    private boolean isBankrupt;
    private String name;
    private ArrayList<PropertyField> fields;
    private int position;

    /**
     * Constructs a player with starting capital of 10,000 and initial board position 0.
     *
     * @param name the display name of the player
     */
    public Player(String name) {
        this.balance = 10000;
        this.isBankrupt = false;
        this.name = name;
        this.fields = new ArrayList<>();
        this.position = 0;
    }

    public ArrayList<PropertyField> getFields() {
        return this.fields;
    }

    public String getName() {
        return this.name;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int amount) {
        this.balance = amount;
    }

    public boolean getBankrupt() {
        return this.isBankrupt;
    }

    public int getPosition() {
        return this.position;
    }

    /**
     * Updates the player's position clockwise along the board
     * using circular modulo indexing.
     *
     * @param steps the number of steps to advance
     * @param boardSize the total number of fields on the board
     */
    public void move(int steps, int boardSize) {
        this.position = (this.position + steps) % boardSize;
    }

    /**
     * Declares the player bankrupt, resets all owned properties back to unowned,
     * and clears the player's property collection.
     */
    public void bankrupt() {
        this.isBankrupt = true;
        for (PropertyField field : this.fields) {
            field.reset();
        }
        this.fields.clear();
    }

    /**
     * Credits money to the player's balance.
     *
     * @param amount the cash amount to add
     */
    public void increaseBalance(int amount) {
        this.balance += amount;
    }

    /**
     * Debits money from the player's balance and automatically triggers
     * bankruptcy if the balance falls below zero.
     *
     * @param amount the cash amount to deduct
     */
    public void decreaseBalance(int amount) {
        this.balance -= amount;
        if (this.balance < 0) {
            this.bankrupt();
        }
    }

    /**
     * Registers a purchased property field into the player's assets.
     *
     * @param property the PropertyField acquired by the player
     */
    public void addProperty(PropertyField property) {
        this.fields.add(property);
    }

    /**
     * Decides whether the player will spend the specified cost on land or a house
     * based on their individual strategy.
     *
     * @param cost the purchase price
     * @return true if the player accepts the purchase; false otherwise
     */
    public abstract boolean shouldBuy(int cost);
}