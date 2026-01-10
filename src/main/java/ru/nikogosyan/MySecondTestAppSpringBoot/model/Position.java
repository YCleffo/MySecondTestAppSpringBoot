package ru.nikogosyan.MySecondTestAppSpringBoot.model;


import lombok.Getter;

@Getter
public enum Position {
    DEV(2.2),
    HR(1.2),
    TL(2.6);

    private final double positionCoefficient;

    Position(double positionCoefficient){
        this.positionCoefficient = positionCoefficient;
    }
}
