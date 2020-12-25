package com.evil.inc.coffeemachine.service;

import com.evil.inc.coffeemachine.domain.CoffeeDrink;
import com.evil.inc.coffeemachine.domain.DrinkSize;
import com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent;
import com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import static com.evil.inc.coffeemachine.domain.CoffeeDrink.AMERICANO;
import static com.evil.inc.coffeemachine.domain.DrinkSize.LARGE;

@Service
@RequiredArgsConstructor
public class CoffeeMachineServiceImpl implements CoffeeMachineService {
    private final StateMachine<CoffeeMachineState, CoffeeMachineEvent> stateMachine;

    @Override
    public void turnOn(){
        stateMachine.sendEvent(CoffeeMachineEvent.TURN_ON);
    }

    @Override
    public void turnOff(){
        stateMachine.sendEvent(CoffeeMachineEvent.TURN_OFF);
    }

    @Override
    public void prepareDrink(DrinkSize drinkSize, CoffeeDrink coffeeDrink){
        stateMachine.getExtendedState().getVariables().put(DrinkSize.class.getName(), drinkSize);
        stateMachine.sendEvent(CoffeeMachineEvent.SELECT_DRINK_SIZE);
        stateMachine.getExtendedState().getVariables().put(CoffeeDrink.class.getName(), coffeeDrink);
        stateMachine.sendEvent(CoffeeMachineEvent.SELECT_DRINK);
        stateMachine.sendEvent(CoffeeMachineEvent.PREPARE_DRINK);
    }
}
