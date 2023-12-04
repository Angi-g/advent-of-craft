package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Hello world!
 */
public class App {
    private static final int MAX_INDEX = 201;

    public static void main(String[] args) throws IOException {
        String contentTest = """
                Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
                Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
                Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
                Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
                Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11""";

        List<String> lines = Files.lines(Paths.get("src/resources/fileToProcess.txt")).collect(Collectors.toList());
        int numberOfMatch ;
        int score = 0;
        int indexCard = 1;
        int[] numberOfCardWonByIndex = initIntArray();
        for (String line : lines) {

            List<int[]> parsedLine = parseLineCard(line);
            numberOfMatch = getNumberOfMatch(parsedLine);
            score += countingScoreByLine(numberOfMatch);
            if (numberOfMatch != 0) {
                updateCardsCount(indexCard, numberOfMatch, numberOfCardWonByIndex);
            }
            indexCard++;
        }
        System.out.println("score = " + score);
        System.out.println("numberOfCardWonTotal = " + calculateSum(numberOfCardWonByIndex));
    }

    static int[] initIntArray() {
        int[] ret = new int[MAX_INDEX];
        Arrays.fill(ret, 1);
        return ret;
    }

    static int calculateSum(int[] integerArray) {
        return Arrays.stream(integerArray).sum();
    }

    static void updateCardsCount(int startingIndex, int numberOfMatch, int[] numberOfCardWonByIndex) {
        int currentIndex = startingIndex;
        while (numberOfMatch > 0) {
            numberOfCardWonByIndex[currentIndex] += numberOfCardWonByIndex[startingIndex - 1];
            currentIndex++;
            numberOfMatch--;
        }

    }

    public static int countingScoreByLine(int numberOfMatch ) {

        return (int) Math.pow(2, numberOfMatch  - 1);
    }

    static int getNumberOfMatch(List<int[]> line) {
        int numberOfMatch = 0;
        int[] numbersYouHave = line.get(1);
        for (int numberToMatch : line.get(0)) {
            if (IntStream.of(numbersYouHave).anyMatch(number -> number == numberToMatch)) {
                numberOfMatch += 1;
            }
        }
        return numberOfMatch;
    }

    static List<int[]> parseLineCard(String lineToParse) {
        // Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        String[] line = lineToParse.replaceAll("Card\\s+\\d+: ", "").split(" \\| ");

        List<int[]> lineList = new ArrayList<>();
        lineList.add(parseStringToIntArray(line[0])); // card
        lineList.add(parseStringToIntArray(line[1])); // numbers you have
        return lineList;
    }

    static int[] parseStringToIntArray(String values) {
        return Arrays.stream(values.trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
    }

}
