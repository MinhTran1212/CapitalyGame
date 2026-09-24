package com.mycompany.mavenproject1;

/**
 * Player implementation following a conservative budget strategy.
 */
public class CarefulPlayer extends Player {

    /**
     * Constructs a CarefulPlayer with the specified name.
     *
     * @param name the player's name
     */
    public CarefulPlayer(String name) {
        super(name);
    }

    /**
     * Evaluates purchasing decision: only buys if the cost does not exceed half of current balance.
     *
     * @param cost the required purchase price
     * @return true if cost is less than or equal to 50% of balance; false otherwise
     */
    @Override
    public boolean shouldBuy(int cost) {
        return cost <= (this.getBalance() / 2);
    }
}