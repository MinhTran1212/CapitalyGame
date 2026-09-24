package com.mycompany.mavenproject1;

/**
 * Abstract base class representing a single field on the Capitaly board.
 * All concrete field types must implement their unique landing behavior.
 */
public abstract class Field {

    /**
     * Executes the interaction when a player lands on this field.
     *
     * @param player the player who stepped onto the field
     */
    public abstract void stepOn(Player player);
}