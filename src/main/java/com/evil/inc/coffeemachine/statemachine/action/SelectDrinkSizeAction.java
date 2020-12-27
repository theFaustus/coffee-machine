package com.evil.inc.coffeemachine.statemachine.action;

import com.evil.inc.coffeemachine.domain.CoffeeDrinkSize;
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
public class SelectDrinkSizeAction implements Action<CoffeeMachineState, CoffeeMachineEvent> {
    @Override
    public void execute(StateContext<CoffeeMachineState, CoffeeMachineEvent> context) {
        CoffeeDrinkSize selectedCoffeeDrinkSize = context.getExtendedState().get(CoffeeDrinkSize.class.getName(), CoffeeDrinkSize.class);
        log.info(context.getSource().getId() + "/" + context.getTarget().getId() + " : Selected size " + selectedCoffeeDrinkSize + "(" + selectedCoffeeDrinkSize.getMultiplicationFactor() + "x)");
    }
}
