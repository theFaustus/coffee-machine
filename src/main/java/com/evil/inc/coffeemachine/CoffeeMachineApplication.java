package com.evil.inc.coffeemachine;

import com.evil.inc.coffeemachine.domain.CoffeeMachineDetails;
import com.evil.inc.coffeemachine.repository.CoffeeMachineDetailsRepository;
import com.evil.inc.coffeemachine.service.CoffeeMachineService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.awt.print.Book;
import java.util.Optional;
import java.util.UUID;

import static com.evil.inc.coffeemachine.domain.CoffeeDrink.AMERICANO;
import static com.evil.inc.coffeemachine.domain.CoffeeDrinkSize.LARGE;

@SpringBootApplication
@EnableRedisRepositories
public class CoffeeMachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeMachineApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(CoffeeMachineService coffeeMachineService, CoffeeMachineDetailsRepository coffeeMachineDetailsRepository) {
        return args -> {
			coffeeMachineService.turnOn();
			coffeeMachineService.prepareDrink(LARGE, AMERICANO);
        };
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

}
