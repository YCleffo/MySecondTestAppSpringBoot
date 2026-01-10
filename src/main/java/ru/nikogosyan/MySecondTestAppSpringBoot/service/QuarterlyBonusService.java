package ru.nikogosyan.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.Position;

@Service
public class QuarterlyBonusService {

    private static final int QUARTER_DAYS = 90;

    public double calculate(Position position, double salary, double bonus, int workDays) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Зарплата должна быть положительным числом");
        }
        if (bonus <= 0) {
            throw new IllegalArgumentException("Коэффициент премии должен быть положительным числом");
        }
        if (workDays <= 0) {
            throw new IllegalArgumentException("Количество рабочих дней должно быть положительным числом");
        }

        // Проверяем, является ли сотрудник управленцем
        if (!position.isManager()) {
            return 0.0;
        }

        // Формула расчета квартальной премии для управленцев
        return salary * bonus * QUARTER_DAYS * position.getPositionCoefficient() / workDays;
    }
}