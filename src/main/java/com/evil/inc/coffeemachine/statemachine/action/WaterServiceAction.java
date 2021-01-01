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

import static com.evil.inc.coffeemachine.domain.CoffeeResource.WATER;

@Component
@RequiredArgsConstructor
@Slf4j
public class WaterServiceAction implements Action<CoffeeMachineState, CoffeeMachineEvent> {
    private final CoffeeMachineDetailsRepository coffeeMachineDetailsRepository;

    @Override
    public void execute(StateContext<CoffeeMachineState, CoffeeMachineEvent> context) {
        String coffeeMachineDetailsId = "4a8955b1-49eb-4417-94c8-13adf6a76635";
        CoffeeMachineDetails coffeeMachineDetails = coffeeMachineDetailsRepository.findById(coffeeMachineDetailsId).orElseThrow(() -> new CoffeeMachineDetailsNotFound("Coffee Machine Details not found on id=" + coffeeMachineDetailsId));
        coffeeMachineDetails.increaseAmountOfWater(500 - coffeeMachineDetails.getAmountOfWater());
        coffeeMachineDetailsRepository.save(coffeeMachineDetails);
        log.info(context.getSource().getId() + "/" + context.getTarget().getId() + " : Water refilled to " + coffeeMachineDetails.getAmountOfWater());
        context.getExtendedState().getVariables().put(WATER, coffeeMachineDetails.getAmountOfWater());
    }
}
