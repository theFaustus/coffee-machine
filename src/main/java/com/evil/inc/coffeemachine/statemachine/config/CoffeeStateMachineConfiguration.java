package com.evil.inc.coffeemachine.statemachine.config;

import com.evil.inc.coffeemachine.domain.CoffeeDrink;
import com.evil.inc.coffeemachine.domain.DrinkSize;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.util.EnumSet;

import static com.evil.inc.coffeemachine.domain.CoffeeDrink.AMERICANO;
import static com.evil.inc.coffeemachine.domain.DrinkSize.LARGE;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent.PREPARE_DRINK;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent.SELECT_DRINK;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent.SELECT_DRINK_SIZE;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent.TURN_OFF;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent.TURN_ON;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.COFFEE_SERVICE;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.DRINK_SIZE_SELECTED;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.MILK_SERVICE;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.OFF;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.PREPARATION_COMPLETE;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.PREPARING;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.STANDBY;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.STANDBY_READY;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.SUGAR_SERVICE;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.WATER_PREHEAT;
import static com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState.WATER_SERVICE;

@Configuration
@EnableStateMachine
public class CoffeeStateMachineConfiguration extends StateMachineConfigurerAdapter<CoffeeMachineState, CoffeeMachineEvent> {

    public static final double BOILING_POINT_DEGREES = 110;
    double coffee = 0;
    double milk = 0;
    double sugar = 0;
    double water = 0;
    double waterTemperature = 49;

    @Override
    public void configure(StateMachineConfigurationConfigurer<CoffeeMachineState, CoffeeMachineEvent> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<CoffeeMachineState, CoffeeMachineEvent> states)
            throws Exception {
        states
                .withStates()
                .initial(OFF)
                .choice(STANDBY)
                .end(OFF)
                .states(EnumSet.allOf(CoffeeMachineState.class));
    }

    @Override
    public void configure(
            StateMachineTransitionConfigurer<CoffeeMachineState, CoffeeMachineEvent> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(OFF).target(STANDBY)
                .event(TURN_ON)
                .action(ctx -> System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Turning on..."))

                .and().withChoice()
                .source(STANDBY)
                .first(COFFEE_SERVICE, notEnoughCoffee(), ctx -> {
                    coffee += 450;
                    System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Coffee refilling..." + coffee);
                })
                .then(MILK_SERVICE, notEnoughMilk(), ctx -> {
                    milk += 450;
                    System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Milk refilling..." + milk);
                })
                .then(SUGAR_SERVICE, notEnoughSugar(), ctx -> {
                    sugar += 450;
                    System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Sugar refilling..." + sugar);
                })
                .then(WATER_SERVICE, notEnoughWater(), ctx -> {
                    water += 450;
                    System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Water refilling..." + water);
                })
                .then(WATER_PREHEAT, coldWater(), ctx -> {
                    waterTemperature += 74;
                    System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Water preheated to " + waterTemperature);
                })
                .last(STANDBY_READY)

                .and().withExternal()
                .source(COFFEE_SERVICE).target(STANDBY)
                .action(ctx -> System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Coffee refilled..."))

                .and().withExternal()
                .source(MILK_SERVICE).target(STANDBY)
                .action(ctx -> System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Milk refilled..."))

                .and().withExternal()
                .source(SUGAR_SERVICE).target(STANDBY)
                .action(ctx -> System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Sugar refilled..."))

                .and().withExternal()
                .source(WATER_SERVICE).target(STANDBY)
                .action(ctx -> System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Water refilled..."))

                .and().withExternal()
                .source(WATER_PREHEAT).target(STANDBY)
                .action(ctx -> System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Water preheated..."))

                .and().withExternal()
                .source(STANDBY_READY).target(DRINK_SIZE_SELECTED)
                .event(SELECT_DRINK_SIZE)
                .action(ctx -> {
                    DrinkSize selectedDrinkSize = ctx.getExtendedState().get(DrinkSize.class.getName(), DrinkSize.class);
                    System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Selected size " + selectedDrinkSize);
                })

                .and().withExternal()
                .source(DRINK_SIZE_SELECTED).target(PREPARING)
                .event(SELECT_DRINK)
                .action(ctx -> {
                    CoffeeDrink selectedDrink = ctx.getExtendedState().get(CoffeeDrink.class.getName(), CoffeeDrink.class);
                    DrinkSize selectedDrinkSize = ctx.getExtendedState().get(DrinkSize.class.getName(), DrinkSize.class);
                    updateMachineResources(selectedDrinkSize, selectedDrink);
                    System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Preparing " + selectedDrinkSize + " " + selectedDrink);
                })

                .and().withExternal()
                .source(PREPARING).target(PREPARATION_COMPLETE)
                .action(ctx -> {
                    CoffeeDrink selectedDrink = ctx.getExtendedState().get(CoffeeDrink.class.getName(), CoffeeDrink.class);
                    DrinkSize selectedDrinkSize = ctx.getExtendedState().get(DrinkSize.class.getName(), DrinkSize.class);
                    System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Ready " + selectedDrinkSize + " " + selectedDrink);
                })
                .event(PREPARE_DRINK)

                .and().withExternal()
                .source(PREPARATION_COMPLETE).target(STANDBY)
                .and().withExternal()

                .source(STANDBY).target(OFF)
                .event(TURN_OFF);
    }

    private void updateMachineResources(DrinkSize selectedDrinkSize, CoffeeDrink coffeeDrink) {
        milk -= selectedDrinkSize.getMultiplicationFactor() * coffeeDrink.getMilkAmount();
        sugar -= selectedDrinkSize.getMultiplicationFactor() * coffeeDrink.getSugarAmount();
        coffee -= selectedDrinkSize.getMultiplicationFactor() * coffeeDrink.getCoffeeAmount();
    }

    @Bean
    public Guard<CoffeeMachineState, CoffeeMachineEvent> coldWater() {
        return ctx -> {
            System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Checking if the water is heated enough..." + waterTemperature);
            return waterTemperature <= BOILING_POINT_DEGREES;
        };
    }

    @Bean
    public Guard<CoffeeMachineState, CoffeeMachineEvent> notEnoughWater() {
        return ctx -> {
            System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Checking if there is enough water..." + water);
            return water == 0;
        };
    }

    @Bean
    public Guard<CoffeeMachineState, CoffeeMachineEvent> notEnoughCoffee() {
        return ctx -> {
            System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Checking if there is enough coffee..." + coffee);
            return coffee == 0;
        };
    }

    @Bean
    public Guard<CoffeeMachineState, CoffeeMachineEvent> notEnoughMilk() {
        return ctx -> {
            System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Checking if there is enough milk..." + milk);
            return milk == 0;
        };
    }

    @Bean
    public Guard<CoffeeMachineState, CoffeeMachineEvent> notEnoughSugar() {
        return ctx -> {
            System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Checking if there is enough sugar..." + sugar);
            return sugar == 0;
        };
    }

    @Bean
    public Action<CoffeeMachineState, CoffeeMachineEvent> coffeeMachinePreConfiguration() {
        return ctx -> System.out.println(ctx.getSource().getId() + "/" + ctx.getTarget().getId() + " : Turning ON the coffee machine...");
    }

}