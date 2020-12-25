package com.evil.inc.coffeemachine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DrinkSize {
    REGULAR(1), LARGE(2);

    private int multiplicationFactor;
}
