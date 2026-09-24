package com.mycompany.mavenproject1;

/**
 * Custom exception thrown when game data from configuration files contains
 * syntactical or logical validation errors.
 */
public class InvalidInputException extends Exception {

    /**
     * Constructs a new InvalidInputException with a descriptive error message.
     *
     * @param message the detail explanation of the validation error
     */
    public InvalidInputException(String message) {
        super(message);
    }
}