package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    void testGetMapedNumberInOneLine() {
        String line = "467..114..";
        List<Number> expectedNumbers = new ArrayList<>();
        expectedNumbers.add(new Number(467, 0, 0, 2));
        expectedNumbers.add(new Number(114, 0, 5, 7));

        List<Number> actualNumber = App.getMapedNumberInOneLine(line, 0);

        for (int i = 0; i < expectedNumbers.size(); i++) {
            Assertions.assertEquals(expectedNumbers.get(i).indexLine, actualNumber.get(i).indexLine);
            Assertions.assertEquals(expectedNumbers.get(i).start, actualNumber.get(i).start);
            Assertions.assertEquals(expectedNumbers.get(i).end, actualNumber.get(i).end);
            Assertions.assertEquals(expectedNumbers.get(i).value, actualNumber.get(i).value);
        }
    }

    @Test
    void testGetMapedSymboleInLine() {
        String line = ".x..#..?..";
        Pattern patternSymbols = Pattern.compile("[^0-9^.]");
        List<int[]> expectedSymboles = new ArrayList<>();
        expectedSymboles.add(new int[]{0, 1});
        expectedSymboles.add(new int[]{0, 4});
        expectedSymboles.add(new int[]{0, 7});

        List<int[]> actualSymboles = App.getMapedSymboleInLine(line, 0, patternSymbols);

        Assertions.assertNotEquals(0, actualSymboles.size());
        for (int i = 0; i < actualSymboles.size(); i++) {
            Assertions.assertArrayEquals(expectedSymboles.get(i), actualSymboles.get(i));
        }
    }

    @Test
    void testValuesOnTopOrBottomOfSymbolWithOneNumberTouchingSymbol() {
        /* 467..114..
           ...*...... */
        List<Number> numbers = new ArrayList<>();
        numbers.add(new Number(467, 0, 0, 2));
        numbers.add(new Number(114, 0, 5, 7));

        int[] symbols = new int[]{1, 3};
        int indexOfSymbol = symbols[0];
        int coloneOfSymbole = symbols[1];
        List<Integer> expectedValue = new ArrayList<>();
        expectedValue.add(467);

        List<Number> toTest = numbers.stream().filter(n -> n.indexLine == (indexOfSymbol - 1)).toList();

        Assertions.assertEquals(expectedValue, App.valuesOnTopOrBottomOfSymbol(coloneOfSymbole, toTest));
    }

    @Test
    void testValuesOnTopOrBottomOfSymbolWithTwoNumbersTouchingSymbol() {
        /* 467.114...
           ...*...... */
        List<Number> numbers = new ArrayList<>();
        numbers.add(new Number(467, 0, 0, 2));
        numbers.add(new Number(114, 0, 4, 6));

        int[] symbols = new int[]{1, 3};

        int indexOfSymbol = symbols[0];
        int coloneOfSymbole = symbols[1];

        List<Integer> expectedValue = new ArrayList<>();
        expectedValue.add(467);
        expectedValue.add(114);

        List<Number> toTest = numbers.stream().filter(n -> n.indexLine == (indexOfSymbol - 1)).toList();

        Assertions.assertEquals(expectedValue, App.valuesOnTopOrBottomOfSymbol(coloneOfSymbole, toTest));
    }

    @Test
    void testValueNextToSymbolWithOneNumberTouchingSymbol() {
        // 467x.114..
        List<Number> numbers = new ArrayList<>();
        numbers.add(new Number(467, 0, 0, 2));
        numbers.add(new Number(114, 0, 5, 7));

        int[] symbols = new int[]{0, 3};
        int indexOfSymbol = symbols[0];
        int coloneOfSymbole = symbols[1];
        List<Integer> expectedValue = new ArrayList<>();
        expectedValue.add(467);

        List<Number> toTest = numbers.stream().filter(n -> n.indexLine == indexOfSymbol).toList();

        Assertions.assertEquals(expectedValue, App.valueNextToSymbole(coloneOfSymbole, toTest));
    }

}

