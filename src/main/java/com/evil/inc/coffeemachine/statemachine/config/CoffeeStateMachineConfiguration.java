package com.evil.inc.coffeemachine.statemachine.config;

import com.evil.inc.coffeemachine.domain.CoffeeDrink;
import com.evil.inc.coffeemachine.domain.CoffeeDrinkSize;
import com.evil.inc.coffeemachine.domain.CoffeeResource;
import com.evil.inc.coffeemachine.statemachine.action.CoffeeServiceAction;
import com.evil.inc.coffeemachine.statemachine.action.MilkServiceAction;
import com.evil.inc.coffeemachine.statemachine.action.PreheatWaterAction;
import com.evil.inc.coffeemachine.statemachine.action.PreparationCompleteAction;
import com.evil.inc.coffeemachine.statemachine.action.PrepareDrinkAction;
import com.evil.inc.coffeemachine.statemachine.action.SelectDrinkAction;
import com.evil.inc.coffeemachine.statemachine.action.SelectDrinkSizeAction;
import com.evil.inc.coffeemachine.statemachine.action.SugarServiceAction;
import com.evil.inc.coffeemachine.statemachine.action.TurnOnAction;
import com.evil.inc.coffeemachine.statemachine.action.WaterServiceAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.util.EnumSet;

import static com.evil.inc.coffeemachine.domain.CoffeeMachineDetails.BOILING_POINT_DEGREES;
import static com.evil.inc.coffeemachine.domain.CoffeeResource.*;
import static com.evil.inc.coffeemachine.domain.CoffeeResource.WATER;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent.PREPARE_DRINK;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent.SELECT_DRINK;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent.SELECT_DRINK_SIZE;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent.TURN_OFF;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent.TURN_ON;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.COFFEE_SERVICE;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.DRINK_SIZE_SELECTED;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.MILK_SERVICE;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.OFF;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.PREPARING_DRINK;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.DRINK_SELECTED;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.STANDBY;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.STANDBY_READY;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.SUGAR_SERVICE;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.WATER_PREHEAT;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.WATER_SERVICE;

@Configuration
@EnableStateMachine
@RequiredArgsConstructor
@Slf4j
public class CoffeeStateMachineConfiguration extends StateMachineConfigurerAdapter<CoffeeMachineState, CoffeeMachineEvent> {

    private final TurnOnAction turnOnAction;
    private final PrepareDrinkAction prepareDrinkAction;
    private final PreheatWaterAction preheatWaterAction;
    private final CoffeeServiceAction coffeeServiceAction;
    private final WaterServiceAction waterServiceAction;
    private final SugarServiceAction sugarServiceAction;
    private final MilkServiceAction milkServiceAction;
    private final SelectDrinkAction selectDrinkAction;
    private final SelectDrinkSizeAction selectDrinkSizeAction;
    private final PreparationCompleteAction preparationCompleteAction;

    @Override
    public void configure(StateMachineConfigurationConfigurer<CoffeeMachineState, CoffeeMachineEvent> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<CoffeeMachineState, CoffeeMachineEvent> states) throws Exception {
        states
                .withStates()
                .initial(OFF)
                .choice(STANDBY)
                .end(OFF)
                .states(EnumSet.allOf(CoffeeMachineState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<CoffeeMachineState, CoffeeMachineEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(OFF).target(STANDBY)
                .event(TURN_ON)
                .action(turnOnAction)

                .and().withChoice()
                .source(STANDBY)
                .first(COFFEE_SERVICE, notEnoughCoffee(), coffeeServiceAction)
                .then(MILK_SERVICE, notEnoughMilk(), milkServiceAction)
                .then(SUGAR_SERVICE, notEnoughSugar(), sugarServiceAction)
                .then(WATER_SERVICE, notEnoughWater(), waterServiceAction)
                .then(WATER_PREHEAT, coldWater(), preheatWaterAction)
                .last(STANDBY_READY)

                .and().withExternal()
                .source(COFFEE_SERVICE).target(STANDBY)
                .action(ctx -> log.info(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Coffee refilled..."))

                .and().withExternal()
                .source(MILK_SERVICE).target(STANDBY)
                .action(ctx -> log.info(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Milk refilled..."))

                .and().withExternal()
                .source(SUGAR_SERVICE).target(STANDBY)
                .action(ctx -> log.info(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Sugar refilled..."))

                .and().withExternal()
                .source(WATER_SERVICE).target(STANDBY)
                .action(ctx -> log.info(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Water refilled..."))

                .and().withExternal()
                .source(WATER_PREHEAT).target(STANDBY)
                .action(ctx -> log.info(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Water preheated..."))

                .and().withExternal()
                .source(STANDBY_READY).target(DRINK_SIZE_SELECTED)
                .event(SELECT_DRINK_SIZE)
                .action(selectDrinkSizeAction)

                .and().withExternal()
                .source(DRINK_SIZE_SELECTED).target(DRINK_SELECTED)
                .event(SELECT_DRINK)
                .action(selectDrinkAction)

                .and().withExternal()
                .source(DRINK_SELECTED).target(PREPARING_DRINK)
                .action(prepareDrinkAction)
                .event(PREPARE_DRINK)

                .and().withExternal()
                .source(PREPARING_DRINK).target(STANDBY)
                .action(preparationCompleteAction)

                .and().withExternal()
                .source(STANDBY).target(OFF)
                .event(TURN_OFF);
    }

    @Bean
    public Guard<CoffeeMachineState, CoffeeMachineEvent> coldWater() {
        return ctx -> {
            Double waterTemperature = ctx.getExtendedState().get(WATER_TEMPERATURE, Double.class);
            log.info(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Checking if the water is heated enough..." + waterTemperature);
            return waterTemperature <= BOILING_POINT_DEGREES;
        };
    }

    @Bean
    public Guard<CoffeeMachineState, CoffeeMachineEvent> notEnoughWater() {
        return ctx -> {
            Double water = ctx.getExtendedState().get(WATER, Double.class);
            log.info(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Checking if there is enough water..." + water);
            return water <= 0;
        };
    }

    @Bean
    public Guard<CoffeeMachineState, CoffeeMachineEvent> notEnoughCoffee() {
        return ctx -> {
            Double coffee = ctx.getExtendedState().get(COFFEE, Double.class);
            log.info(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Checking if there is enough coffee..." + coffee);
            return coffee <= 0;
        };
    }

    @Bean
    public Guard<CoffeeMachineState, CoffeeMachineEvent> notEnoughMilk() {
        return ctx -> {
            Double milk = ctx.getExtendedState().get(MILK, Double.class);
            log.info(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Checking if there is enough milk..." + milk);
            return milk <= 0;
        };
    }

    @Bean
    public Guard<CoffeeMachineState, CoffeeMachineEvent> notEnoughSugar() {
        return ctx -> {
            Double sugar = ctx.getExtendedState().get(SUGAR, Double.class);
            log.info(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Checking if there is enough sugar..." + sugar);
            return sugar <= 0;
        };
    }

}