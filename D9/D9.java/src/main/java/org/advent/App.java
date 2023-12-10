package org.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        // List<String> lines = Files.lines(Paths.get("src/resources/exemple.txt")).toList();
        List<String> lines = Files.lines(Paths.get("src/resources/fileToProcess.txt")).toList();
        List<int[]> parsedLines = lines.stream()
                .map(line -> Arrays.stream(line.split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toList();

        //  long totalExtrapolatedNextValue = partOne(parsedLines);
        long totalExtrapolatedNextValuePartTwo = partTwo(parsedLines);
        //  System.out.println("part one : " + totalExtrapolatedNextValue);
        System.out.println("part two : " + totalExtrapolatedNextValuePartTwo);
    }

    private static long partOne(List<int[]> parsedLines) {
        return parsedLines.stream()
                .mapToLong(App::getExtrapolation)
                .sum();
    }

    private static long partTwo(List<int[]> parsedLines) {
        return parsedLines.stream()
                .mapToLong(App::getExtrapolationPartTwo)
                .sum();
    }

    private static int getExtrapolationPartTwo(int[] parsedLine) {

        List<int[]> mapExtrapolations = getListMapExtrapolation(parsedLine);

        List<int[]> newMapExtrapolations = new ArrayList<>();
        mapExtrapolations.forEach(m -> newMapExtrapolations.add(addToBeginning(m)));

        IntStream.range(0, mapExtrapolations.size())
                .boxed()
                .sorted(Comparator.reverseOrder())
                .reduce((prevIndex, actualIndex) -> {
                    int[] actualNode = mapExtrapolations.get(actualIndex);
                    int[] previousNode = newMapExtrapolations.get(prevIndex);
                    int value = actualNode[0] - previousNode[0];
                    newMapExtrapolations.get(actualIndex)[0] = value;
                    return actualIndex;
                });

        return newMapExtrapolations.get(0)[0];
    }

    private static List<int[]> getListMapExtrapolation(int[] parsedLine) {
        List<int[]> mapExtrapolations = new ArrayList<>();
        int[] mapExtrapolation = parsedLine;
        mapExtrapolations.add(mapExtrapolation);
        mapExtrapolations.add(getMapExtrapolations(parsedLine));
        while (!isMapExtrapolationLast(mapExtrapolation)) {
            mapExtrapolation = getMapExtrapolations(mapExtrapolations.get(mapExtrapolations.size() - 1));
            mapExtrapolations.add(mapExtrapolation);
        }
        return mapExtrapolations;
    }

    private static int[] addToBeginning(int[] originalArray) {
        int newArrayLength = originalArray.length + 1;
        int[] newArray = new int[newArrayLength];

        System.arraycopy(originalArray, 0, newArray, 1, originalArray.length);

        newArray[0] = 0;

        return newArray;
    }

    private static int getExtrapolation(int[] parsedLine) {
        List<int[]> mapExtrapolations = getListMapExtrapolation(parsedLine);

        IntStream.range(0, mapExtrapolations.size())
                .boxed()
                .sorted(Comparator.reverseOrder())
                .reduce((prevIndex, actualIndex) -> {
                    int[] actualNode = mapExtrapolations.get(actualIndex);
                    int[] previousNode = mapExtrapolations.get(prevIndex);
                    actualNode[actualNode.length - 1] = previousNode[previousNode.length - 1] + actualNode[previousNode.length];
                    return actualIndex;
                });

        return mapExtrapolations.get(0)[mapExtrapolations.get(0).length - 1];
    }

    private static int[] getMapExtrapolations(int[] values) {

        return IntStream.range(0, values.length - 1)
                .mapToObj(i -> values[i + 1] - values[i])
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private static boolean isMapExtrapolationLast(int[] mapExtrapolation) {

        return Arrays.stream(mapExtrapolation).allMatch(map -> map == 0);
    }


}
