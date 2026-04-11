package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LionTest {

    @Mock
    private Feline feline;

    @Test
    void lionMaleHasMane() throws Exception {
        Lion lion = new Lion("Самец", feline);
        assertTrue(lion.doesHaveMane());
    }

    @Test
    void lionFemaleDoesNotHaveMane() throws Exception {
        Lion lion = new Lion("Самка", feline);
        assertFalse(lion.doesHaveMane());
    }

    @Test
    void lionInvalidSexThrowsException() {
        Exception exception = assertThrows(Exception.class, () -> new Lion("Неизвестный", feline));
        assertEquals("Используйте допустимые значения пола животного - самец или самка", exception.getMessage());
    }

    @Test
    void getKittens() throws Exception {
        Mockito.when(feline.getKittens()).thenReturn(3);
        Lion lion = new Lion("Самец", feline);

        int actual = lion.getKittens();

        assertEquals(3, actual);
    }

    @Test
    void getFood() throws Exception {
        List<String> expectedFood = List.of("Животные", "Птицы", "Рыба");
        Mockito.when(feline.getFood("Хищник")).thenReturn(expectedFood);

        Lion lion = new Lion("Самка", feline);
        List<String> actual = lion.getFood();

        assertEquals(expectedFood, actual);
    }

    @Test
    void getFoodThrowsException() throws Exception {
        Mockito.when(feline.getFood("Хищник")).thenThrow(new Exception("Ошибка получения пищи"));

        Lion lion = new Lion("Самец", feline);

        Exception exception = assertThrows(Exception.class, lion::getFood);
        assertEquals("Ошибка получения пищи", exception.getMessage());
    }
}