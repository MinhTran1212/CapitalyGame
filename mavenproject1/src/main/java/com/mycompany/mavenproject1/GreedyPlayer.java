package com.mycompany.mavenproject1;

/**
 * Player implementation following a greedy purchasing strategy.
 */
public class GreedyPlayer extends Player {

    /**
     * Constructs a GreedyPlayer with the specified name.
     *
     * @param name the player's name
     */
    public GreedyPlayer(String name) {
        super(name);
    }

    /**
     * Evaluates purchasing decision: always buys if balance covers the cost.
     *
     * @param cost the required purchase price
     * @return true if balance is greater than or equal to cost; false otherwise
     */
    @Override
    public boolean shouldBuy(int cost) {
        return this.getBalance() >= cost;
    }
}