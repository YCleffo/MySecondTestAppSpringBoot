package ru.nikogosyan.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.Position;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@ExtendWith(MockitoExtension.class)
class QuarterlyBonusServiceTest {

    private final QuarterlyBonusService quarterlyBonusService = new QuarterlyBonusService();

    @Test
    void calculate_ForManager_ReturnsCorrectBonus() {
        // given
        Position position = Position.MANAGER; // isManager = true
        double salary = 100000.0;
        double bonus = 1.5;
        int workDays = 60;

        // when
        double result = quarterlyBonusService.calculate(position, salary, bonus, workDays);

        // then
        double expected = 100000.0 * 1.5 * 90 * 2.8 / 60; // 630000.0
        assertThat(result).isCloseTo(expected, within(0.01));
    }

    @Test
    void calculate_ForNonManager_ReturnsZero() {
        // given
        Position position = Position.DEV; // isManager = false
        double salary = 100000.0;
        double bonus = 1.5;
        int workDays = 60;

        // when
        double result = quarterlyBonusService.calculate(position, salary, bonus, workDays);

        // then
        assertEquals(0.0, result, 0.01);
    }

    @Test
    void calculate_ForDirector_ReturnsCorrectBonus() {
        // given
        Position position = Position.DIRECTOR; // isManager = true
        double salary = 150000.0;
        double bonus = 2.0;
        int workDays = 65;

        // when
        double result = quarterlyBonusService.calculate(position, salary, bonus, workDays);

        // then
        double expected = 150000.0 * 2.0 * 90 * 3.0 / 65;
        assertThat(result).isCloseTo(expected, within(0.01));
    }

    @Test
    void calculate_WithInvalidSalary_ThrowsException() {
        // given
        Position position = Position.MANAGER;
        double invalidSalary = -1000.0;
        double bonus = 1.5;
        int workDays = 60;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            quarterlyBonusService.calculate(position, invalidSalary, bonus, workDays);
        });
    }

    @Test
    void calculate_WithInvalidWorkDays_ThrowsException() {
        // given
        Position position = Position.MANAGER;
        double salary = 100000.0;
        double bonus = 1.5;
        int invalidWorkDays = 0;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            quarterlyBonusService.calculate(position, salary, bonus, invalidWorkDays);
        });
    }
}