package ru.nikogosyan.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.Position;

@Service
public interface AnnualBonusService {
    double calculate(Position position, double salary, double bonus, int workDays);
}
