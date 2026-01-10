package ru.nikogosyan.MySecondTestAppSpringBoot.model;


import lombok.Getter;

@Getter
public enum Position {
    DEV(2.2, false),
    HR(1.2, false),
    TL(2.6, false),
    MANAGER(2.8, true),
    DIRECTOR(3.0, true),
    TEAM_LEAD(2.7, true),
    SENIOR_DEV(2.4, false),
    JUNIOR_DEV(1.8, false);

    private final double positionCoefficient;
    private final boolean isManager;

    Position(double positionCoefficient, boolean isManager) {
        this.positionCoefficient = positionCoefficient;
        this.isManager = isManager;
    }
}
