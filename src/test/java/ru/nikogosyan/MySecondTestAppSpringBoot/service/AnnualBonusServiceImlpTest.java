package ru.nikogosyan.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.Position;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AnnualBonusServiceImlpTest {

    @Test
    void calculate() {
        // given
        Position position = Position.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        // when
        double result = new AnnualBonusServiceImlp().calculate(position, salary, bonus, workDays);

        // then
        double expected = 360493.8271604938;
        assertThat(result).isEqualTo(expected);
    }
}