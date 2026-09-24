package com.mycompany.mavenproject1;

/**
 * Represents a service field that requires players landing on it
 * to pay a mandatory service fee to the bank.
 */
public class ServiceField extends Field {
    private int fee;

    /**
     * Constructs a ServiceField with a specific mandatory fee.
     *
     * @param fee the fee amount deducted from visiting players
     */
    public ServiceField(int fee) {
        this.fee = fee;
    }

    public int getFee() {
        return this.fee;
    }

    /**
     * Deducts the service fee directly from the visiting player's balance.
     *
     * @param player the player who landed on this service field
     */
    @Override
    public void stepOn(Player player) {
        player.decreaseBalance(this.fee);
    }
}