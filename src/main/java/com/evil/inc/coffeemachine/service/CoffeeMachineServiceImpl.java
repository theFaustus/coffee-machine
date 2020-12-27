package com.evil.inc.coffeemachine.service;

import com.evil.inc.coffeemachine.domain.CoffeeDrink;
import com.evil.inc.coffeemachine.domain.CoffeeDrinkSize;
import com.evil.inc.coffeemachine.domain.CoffeeMachineDetails;
import com.evil.inc.coffeemachine.exception.CoffeeMachineDetailsNotFound;
import com.evil.inc.coffeemachine.repository.CoffeeMachineDetailsRepository;
import com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent;
import com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoffeeMachineServiceImpl implements CoffeeMachineService {
    private final StateMachine<CoffeeMachineState, CoffeeMachineEvent> stateMachine;
    private final CoffeeMachineDetailsRepository coffeeMachineDetailsRepository;

    @Override
    public void turnOn(){
        stateMachine.sendEvent(CoffeeMachineEvent.TURN_ON);
    }

    @Override
    public void turnOff(){
        stateMachine.sendEvent(CoffeeMachineEvent.TURN_OFF);
    }

    @Override
    public void prepareDrink(CoffeeDrinkSize coffeeDrinkSize, CoffeeDrink coffeeDrink){
        stateMachine.getExtendedState().getVariables().put(CoffeeDrinkSize.class.getName(), coffeeDrinkSize);
        stateMachine.sendEvent(CoffeeMachineEvent.SELECT_DRINK_SIZE);
        stateMachine.getExtendedState().getVariables().put(CoffeeDrink.class.getName(), coffeeDrink);
        stateMachine.sendEvent(CoffeeMachineEvent.SELECT_DRINK);
        stateMachine.sendEvent(CoffeeMachineEvent.PREPARE_DRINK);
    }
}
