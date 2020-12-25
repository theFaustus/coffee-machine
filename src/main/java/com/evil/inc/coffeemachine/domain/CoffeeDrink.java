package com.evil.inc.coffeemachine.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CoffeeDrink {
    ESPRESSO(30.0, 5.0, 0.0),
    AMERICANO(60.0, 5.0, 0.0),
    CAPPUCCINO(30.0, 15.0, 30.0),
    MOCHA(30.0, 15.0,50.0);

    private double coffeeAmount;
    private double sugarAmount;
    private double milkAmount;

}
