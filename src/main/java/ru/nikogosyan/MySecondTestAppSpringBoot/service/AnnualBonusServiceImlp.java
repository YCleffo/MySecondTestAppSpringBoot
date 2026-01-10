package ru.nikogosyan.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.Position;

@Service
public class AnnualBonusServiceImlp implements  AnnualBonusService{
    @Override
    public double calculate(Position position, double salary, double bonus, int workDays){
        int currentYear = java.time.Year.now().getValue();
        int daysInYear = isLeapYear(currentYear) ? 366 : 365;

        return salary * bonus * daysInYear * position.getPositionCoefficient() / workDays;
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
