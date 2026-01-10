package ru.nikogosyan.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.Position;

@Service
public class AnnualBonusServiceImlp implements  AnnualBonusService{
    @Override
    public double calculate(Position position, double salary, double bonus, int workDays){
        return salary * bonus * 365 * position.getPositionCoefficient() / workDays;
    }
}
