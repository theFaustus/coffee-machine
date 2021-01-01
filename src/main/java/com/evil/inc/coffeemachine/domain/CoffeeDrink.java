package com.evil.inc.coffeemachine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CoffeeDrink {
    ESPRESSO(30.0, 5.0, 0.0, 40.0),
    AMERICANO(60.0, 5.0, 0.0, 80.0),
    CAPPUCCINO(30.0, 15.0, 30.0, 80.0),
    MOCHA(30.0, 15.0,50.0, 80.0);

    private final double coffeeAmount;
    private final double sugarAmount;
    private final double milkAmount;
    private final double waterAmount;

}
