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

import static com.evil.inc.coffeemachine.domain.CoffeeResource.COFFEE;
import static com.evil.inc.coffeemachine.domain.CoffeeResource.MILK;
import static com.evil.inc.coffeemachine.domain.CoffeeResource.SUGAR;
import static com.evil.inc.coffeemachine.domain.CoffeeResource.WATER;
import static com.evil.inc.coffeemachine.domain.CoffeeResource.WATER_TEMPERATURE;

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
        context.getExtendedState().getVariables().put(MILK, coffeeMachineDetails.getAmountOfMilk());
        context.getExtendedState().getVariables().put(COFFEE, coffeeMachineDetails.getAmountOfCoffee());
        context.getExtendedState().getVariables().put(SUGAR, coffeeMachineDetails.getAmountOfSugar());
        context.getExtendedState().getVariables().put(WATER, coffeeMachineDetails.getAmountOfWater());
        context.getExtendedState().getVariables().put(WATER_TEMPERATURE, coffeeMachineDetails.getWaterTemperature());
    }
}
