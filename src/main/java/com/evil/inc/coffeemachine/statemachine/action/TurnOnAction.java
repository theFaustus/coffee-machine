package com.evil.inc.coffeemachine.statemachine.action;

import com.evil.inc.coffeemachine.domain.CoffeeMachineDetails;
import com.evil.inc.coffeemachine.exception.CoffeeMachineDetailsNotFound;
import com.evil.inc.coffeemachine.repository.CoffeeMachineDetailsRepository;
import com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent;
import com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TurnOnAction implements Action<CoffeeMachineState, CoffeeMachineEvent> {
    private final CoffeeMachineDetailsRepository coffeeMachineDetailsRepository;

    @Override
    public void execute(StateContext<CoffeeMachineState, CoffeeMachineEvent> context) {
        log.info(context.getSource().getId() + "/" + context.getTarget().getId() + " : Turning on...");
        String coffeeMachineDetailsId = "4a8955b1-49eb-4417-94c8-13adf6a76635";
        CoffeeMachineDetails coffeeMachineDetails = coffeeMachineDetailsRepository.findById(coffeeMachineDetailsId).orElseThrow(() -> new CoffeeMachineDetailsNotFound("Coffee Machine Details not found on id=" + coffeeMachineDetailsId));
        log.info("Current coffee machine details resources {}", coffeeMachineDetails);
        updateStateMachineVariables(context, coffeeMachineDetails);
    }

    private void updateStateMachineVariables(StateContext<CoffeeMachineState, CoffeeMachineEvent> context, CoffeeMachineDetails coffeeMachineDetails) {
        context.getExtendedState().getVariables().put("milk", coffeeMachineDetails.getAmountOfMilk());
        context.getExtendedState().getVariables().put("coffee", coffeeMachineDetails.getAmountOfCoffee());
        context.getExtendedState().getVariables().put("sugar", coffeeMachineDetails.getAmountOfSugar());
        context.getExtendedState().getVariables().put("water", coffeeMachineDetails.getAmountOfWater());
        context.getExtendedState().getVariables().put("waterTemperature", coffeeMachineDetails.getWaterTemperature());
    }
}
