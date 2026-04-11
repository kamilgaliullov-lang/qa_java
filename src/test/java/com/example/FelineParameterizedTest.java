package com.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class FelineParameterizedTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 5, 10})
    void getKittensWithDifferentCounts(int kittensCount) {
        Feline feline = new Feline();
        int actual = feline.getKittens(kittensCount);
        assertEquals(kittensCount, actual,
                "Метод getKittens должен вернуть переданное значение: " + kittensCount);
    }
}
