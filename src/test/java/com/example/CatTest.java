package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CatTest {

    @Mock
    private Feline feline;

    private Cat cat;

    @BeforeEach
    void setUp() {
        cat = new Cat(feline);
    }

    @Test
    void getSound() {
        String expected = "Мяу";
        String actual = cat.getSound();
        assertEquals(expected, actual, "Метод getSound должен вернуть 'Мяу'");
    }

    @Test
    void getFood() throws Exception {
        List<String> expectedFood = List.of("Животные", "Птицы", "Рыба");
        Mockito.when(feline.eatMeat()).thenReturn(expectedFood);

        List<String> actual = cat.getFood();

        assertEquals(expectedFood, actual, "Метод getFood должен вернуть список пищи от хищника");
        Mockito.verify(feline, Mockito.times(1)).eatMeat();
    }

    @Test
    void getFoodThrowsException() throws Exception {
        Mockito.when(feline.eatMeat()).thenThrow(new Exception("Ошибка получения пищи"));

        Exception exception = assertThrows(Exception.class, () -> cat.getFood());
        assertEquals("Ошибка получения пищи", exception.getMessage());
    }
}
