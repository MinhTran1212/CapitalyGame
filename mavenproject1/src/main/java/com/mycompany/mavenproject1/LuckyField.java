package com.mycompany.mavenproject1;

/**
 * Represents a lucky field that awards a cash bonus to any player who lands on it.
 */
public class LuckyField extends Field {
    private int reward;

    /**
     * Constructs a LuckyField with a designated reward amount.
     *
     * @param reward the bonus cash awarded to visiting players
     */
    public LuckyField(int reward) {
        this.reward = reward;
    }

    public int getReward() {
        return this.reward;
    }

    /**
     * Credits the reward amount directly to the visiting player's balance.
     *
     * @param player the player who landed on this lucky field
     */
    @Override
    public void stepOn(Player player) {
        player.increaseBalance(this.reward);
    }
}