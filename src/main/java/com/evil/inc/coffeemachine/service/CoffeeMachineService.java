package com.evil.inc.coffeemachine.service;

import com.evil.inc.coffeemachine.domain.CoffeeDrink;
import com.evil.inc.coffeemachine.domain.DrinkSize;

public interface CoffeeMachineService {
    void turnOn();
    void turnOff();

    void prepareDrink(DrinkSize drinkSize, CoffeeDrink coffeeDrink);
}
