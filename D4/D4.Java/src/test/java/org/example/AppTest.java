package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    void testParselineCard() {
        String lineToParse = " Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53";
        int[] card = new int[]{41, 48, 83, 86, 17};
        int[] numberYouHave = new int[]{83, 86, 6, 31, 17, 9, 48, 53};
        List<int[]> expectedResult = new ArrayList<>();
        expectedResult.add(card);
        expectedResult.add(numberYouHave);

        List<int[]> actualResult = App.parseLineCard(lineToParse);
        Assertions.assertArrayEquals(expectedResult.get(0), actualResult.get(0));
        Assertions.assertArrayEquals(expectedResult.get(1), actualResult.get(1));
    }

    @Test
    void testCountingScoreByLine() {
        int[] card = new int[]{41, 48, 83, 86, 17};
        int[] numberYouHave = new int[]{83, 86, 6, 31, 17, 9, 48, 53};
        List<int[]> line = new ArrayList<>();
        line.add(card);
        line.add(numberYouHave);
        int expectedResult = 8;
        int actualResult = App.numberOfMatchInLine(line);
        Assertions.assertEquals(expectedResult, App.countingScoreByLine(actualResult));
    }

    @Test
    void testCalculateSum() {
        int[] array = {1, 2, 3, 4, 5};
        int expectedResult = 15;
        int actualResult = App.calculateSum(array);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testAddCardsWonToCount() {
        int indexCard = 2;
        int numberOfMatchByLine = 3;
        int[] numberOfCardWonByIndex = {1, 2, 3, 4, 1, 1};
        int[] expectedArray = {1, 2, 5, 6, 3, 1};
        App.addCardsWonToCount(indexCard, numberOfMatchByLine, numberOfCardWonByIndex);
        Assertions.assertArrayEquals(expectedArray, numberOfCardWonByIndex);
    }
}

