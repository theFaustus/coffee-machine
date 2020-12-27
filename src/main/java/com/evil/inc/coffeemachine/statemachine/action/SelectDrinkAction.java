package com.evil.inc.coffeemachine.statemachine.action;

import com.evil.inc.coffeemachine.domain.CoffeeDrink;
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
public class SelectDrinkAction implements Action<CoffeeMachineState, CoffeeMachineEvent> {
    @Override
    public void execute(StateContext<CoffeeMachineState, CoffeeMachineEvent> context) {
        CoffeeDrink selectedCoffeeDrink = context.getExtendedState().get(CoffeeDrink.class.getName(), CoffeeDrink.class);
        log.info(context.getSource().getId() + "/" + context.getTarget().getId() + " : Selected drink "
                + selectedCoffeeDrink + "("
                + selectedCoffeeDrink.getCoffeeAmount() + ", "
                + selectedCoffeeDrink.getWaterAmount() + ", "
                + selectedCoffeeDrink.getMilkAmount() + ", "
                + selectedCoffeeDrink.getSugarAmount() + ")");    }
}
