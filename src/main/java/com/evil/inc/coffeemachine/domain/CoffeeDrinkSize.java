package com.evil.inc.coffeemachine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CoffeeDrinkSize {
    REGULAR(1), LARGE(2);

    private final int multiplicationFactor;
}
