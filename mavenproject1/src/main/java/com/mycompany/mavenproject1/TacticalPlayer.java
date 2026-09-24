package com.mycompany.mavenproject1;

/**
 * Player implementation following an alternating skip strategy.
 */
public class TacticalPlayer extends Player {
    private boolean canBuy;

    /**
     * Constructs a TacticalPlayer with the specified name.
     * Starts with the ability to buy enabled.
     *
     * @param name the player's name
     */
    public TacticalPlayer(String name) {
        super(name);
        this.canBuy = true;
    }

    /**
     * Evaluates purchasing decision: strictly alternates between buying and skipping
     * on each available purchase opportunity.
     *
     * @param cost the required purchase price
     * @return true if it is an active turn and balance covers the cost; false otherwise
     */
    @Override
    public boolean shouldBuy(int cost) {
        if (this.canBuy) {
            this.canBuy = false;
            return this.getBalance() >= cost;
        } else {
            this.canBuy = true;
            return false;
        }
    }
}