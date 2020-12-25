package com.evil.inc.coffeemachine.exception;

public class NotEnoughCoffeeException extends RuntimeException {
    public NotEnoughCoffeeException(String s) {
        super(s);
    }
}
