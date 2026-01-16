package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FelineTest {

    @Spy
    private Feline feline;

    @Test
    void eatMeat() throws Exception {
        List<String> expected = List.of("Животные", "Птицы", "Рыба");
        List<String> actual = feline.eatMeat();
        assertEquals(expected, actual, "Метод eatMeat должен вернуть список пищи для хищника");
    }

    @Test
    void getFamily() {
        String expected = "Кошачьи";
        String actual = feline.getFamily();
        assertEquals(expected, actual, "Метод getFamily должен вернуть 'Кошачьи'");
    }

    @Test
    void getKittensWithoutParameters() {
        int expected = 1;
        int actual = feline.getKittens();
        assertEquals(expected, actual, "Метод getKittens без параметров должен вернуть 1");
    }

    @Test
    void getKittensWithParameter() {
        int kittensCount = 5;
        int actual = feline.getKittens(kittensCount);
        assertEquals(kittensCount, actual, "Метод getKittens с параметром должен вернуть переданное значение");
    }

    @Test
    void getKittensCallsOverloadedMethod() {
        Feline spyFeline = Mockito.spy(new Feline());
        spyFeline.getKittens();
        Mockito.verify(spyFeline, Mockito.times(1)).getKittens(1);
    }
}
