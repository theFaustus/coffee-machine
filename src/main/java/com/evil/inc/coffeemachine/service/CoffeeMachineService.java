package com.evil.inc.coffeemachine.service;

import com.evil.inc.coffeemachine.domain.CoffeeDrink;
import com.evil.inc.coffeemachine.domain.CoffeeDrinkSize;

public interface CoffeeMachineService {
    void turnOn();
    void turnOff();

    void prepareDrink(CoffeeDrinkSize coffeeDrinkSize, CoffeeDrink coffeeDrink);
}
