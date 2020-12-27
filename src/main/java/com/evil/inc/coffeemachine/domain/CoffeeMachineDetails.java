package com.evil.inc.coffeemachine.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@RedisHash("CoffeeMachineDetails")
public class CoffeeMachineDetails {
    public static final double BOILING_POINT_DEGREES = 100;

    private String id;

    private String name;
    private String brand;

    private double amountOfCoffee;
    private double amountOfMilk;
    private double amountOfSugar;
    private double amountOfWater;
    private double waterTemperature;

    public void decreaseAmountOfCoffee(double amountOfCoffee){
        this.amountOfCoffee -= amountOfCoffee;
    }

    public void decreaseAmountOfMilk(double amountOfMilk){
        this.amountOfMilk -= amountOfMilk;
    }

    public void decreaseAmountOfSugar(double amountOfSugar){
        this.amountOfSugar -= amountOfSugar;
    }

    public void decreaseAmountOfWater(double amountOfWater){
        this.amountOfWater -= amountOfWater;
    }

    public void decreaseWaterTemperature(double waterTemperature){
        this.waterTemperature -= waterTemperature;
    }

    public void increaseAmountOfCoffee(double amountOfCoffee){
        this.amountOfCoffee += amountOfCoffee;
    }

    public void increaseAmountOfMilk(double amountOfMilk){
        this.amountOfMilk += amountOfMilk;
    }

    public void increaseAmountOfSugar(double amountOfSugar){
        this.amountOfSugar += amountOfSugar;
    }

    public void increaseAmountOfWater(double amountOfWater){
        this.amountOfWater += amountOfWater;
    }

    public void increaseWaterTemperature(double waterTemperature){
        this.waterTemperature += waterTemperature;
    }
}
