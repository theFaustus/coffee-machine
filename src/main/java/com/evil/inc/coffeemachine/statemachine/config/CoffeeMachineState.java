package com.evil.inc.coffeemachine.statemachine.config;

public enum CoffeeMachineState {
    OFF, STANDBY, STANDBY_READY, DRINK_SIZE_SELECTED,
    DRINK_SELECTED, PREPARING_DRINK, MILK_SERVICE,
    COFFEE_SERVICE, SUGAR_SERVICE, WATER_SERVICE, WATER_PREHEAT, ERROR
}
