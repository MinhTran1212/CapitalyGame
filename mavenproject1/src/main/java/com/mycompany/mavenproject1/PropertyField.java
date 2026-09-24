package com.mycompany.mavenproject1;

/**
 * Represents a purchasable property field where players can buy land,
 * construct houses, and collect rent from other players.
 */
public class PropertyField extends Field {
    private boolean hasHouse;
    private Player owner;

    /**
     * Constructs a new unowned PropertyField with no house built.
     */
    public PropertyField() {
        this.hasHouse = false;
        this.owner = null;
    }

    public Player getOwner() {
        return this.owner;
    }

    public boolean hasHouse() {
        return this.hasHouse;
    }

    /**
     * Handles the interaction when a player lands on the property.
     * Buys the land if unowned, builds a house if stepped on by the owner,
     * or charges rent if stepped on by an opponent.
     *
     * @param player the player who landed on this property
     */
    @Override
    public void stepOn(Player player) {
        if (owner == null) {
            if (player.shouldBuy(1000)) {
                player.decreaseBalance(1000);
                this.owner = player;
                player.addProperty(this);
            }
        } else if (player == this.owner) {
            if (!this.hasHouse && player.shouldBuy(4000)) {
                player.decreaseBalance(4000);
                this.hasHouse = true;
            }
        } else if (!this.hasHouse) {
            if (player.getBalance() < 500) {
                this.owner.increaseBalance(player.getBalance());
                player.decreaseBalance(500);
            } else {
                player.decreaseBalance(500);
                this.owner.increaseBalance(500);
            }
        } else {
            if (player.getBalance() < 2000) {
                this.owner.increaseBalance(player.getBalance());
                player.decreaseBalance(2000);
            } else {
                player.decreaseBalance(2000);
                this.owner.increaseBalance(2000);
            }
        }
    }

    /**
     * Resets the property to its initial unowned state and removes any house.
     * Called when the current owner goes bankrupt.
     */
    public void reset() {
        this.hasHouse = false;
        this.owner = null;
    }
}