package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testExtractNumberInlineWithAnEmptyuLine() {
        Integer expectedValue = 0;
        String lineToTest = "";

        Assertions.assertEquals(expectedValue, App.extractNumberInline(lineToTest));
    }

    @Test
    public void testExtractNumberInlineWithSimpleLine() {
        Integer expectedValue = 12;
        String lineToTest = "1abc2";

        Assertions.assertEquals(expectedValue, App.extractNumberInline(lineToTest));
    }

    @Test
    public void testExtractNumberInlineWithTrickyLine() {
        Integer expectedValue = 15;
        String lineToTest = "\"a1b2c3d4e5f";

        Assertions.assertEquals(expectedValue, App.extractNumberInline(lineToTest));
    }

    @Test
    public void testExtractAllNumberInMultipleLines() {
        List<Integer> expectedValue = Arrays.asList(12, 38, 15, 77, 91);
        String lineToTest = "1abc2\n" +
                "pqr3stu8vwx\n" +
                "a1b2c3d4e5f\n" +
                "treb7uchet\n" +
                "pcg91vqrfpxxzzzoneightzt";

        Assertions.assertEquals(expectedValue, App.extractAllNumberInMultipleLines(lineToTest));
    }

    @Test
    public void testnumberandDigitInLine() {
        List<Integer> expectedValue = Arrays.asList(98);
        String lineToTest = "pcg91vqrfpxxzzztwoeightzt";
        String expectedLine = "pcg91vqrfpxxzzztwo2woeight8ightzt";
        Assertions.assertEquals(expectedLine, App.replaceCharNumberByDigit(lineToTest));
        Assertions.assertEquals(expectedValue, App.extractAllNumberInMultipleLines(App.replaceCharNumberByDigit(lineToTest)));
    }

    @Test
    public void testCalculateFinalValue() {
        Integer expectedValue = 142;
        List<Integer> valueToTest = Arrays.asList(12, 38, 15, 77);

        Assertions.assertEquals(expectedValue, App.calculateFinalValue(valueToTest));
    }

    @Test
    public void testreplaceCharNumberByDigit() {
        String lineToTest = "two1nine\n" +
                "eightwothree\n" +
                "abcone2threexyz\n" +
                "xtwone3four\n" +
                "4nineeightseven2\n" +
                "zoneight234\n" +
                "7pqrstsixteen";

        String expectedline = "two2wo1nine9ine\n" +
                "eight8ightwo2wothree3hree\n" +
                "abcone1ne2three3hreexyz\n" +
                "xtwo2wone1ne3four4our\n" +
                "4nine9ineeight8ightseven7even2\n" +
                "zone1neight8ight234\n" +
                "7pqrstsix6ixteen";

        Assertions.assertEquals(expectedline, App.replaceCharNumberByDigit(lineToTest));

    }
}
