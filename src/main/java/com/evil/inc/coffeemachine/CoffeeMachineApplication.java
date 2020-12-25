package com.evil.inc.coffeemachine;

import com.evil.inc.coffeemachine.domain.CoffeeDrink;
import com.evil.inc.coffeemachine.domain.DrinkSize;
import com.evil.inc.coffeemachine.service.CoffeeMachineService;
import com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineEvent;
import com.evil.inc.coffeemachine.statemachine.config.CoffeeMachineState;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

import static com.evil.inc.coffeemachine.domain.CoffeeDrink.AMERICANO;
import static com.evil.inc.coffeemachine.domain.CoffeeDrink.MOCHA;
import static com.evil.inc.coffeemachine.domain.DrinkSize.LARGE;
import static com.evil.inc.coffeemachine.domain.DrinkSize.REGULAR;

@SpringBootApplication
public class CoffeeMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeMachineApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(CoffeeMachineService coffeeMachineService){
		return args -> {
			coffeeMachineService.turnOn();
			coffeeMachineService.prepareDrink(LARGE, AMERICANO);
		};
	}

}
