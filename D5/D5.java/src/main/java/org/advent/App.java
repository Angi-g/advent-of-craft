package org.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Hello world!
 */
public class App {
    public static final int START_SRC = 1;
    public static final int START_DEST = 0;
    public static final int RANGE = 2;

    public static void main(String[] args) throws IOException {
        //List<String> lines = Files.lines(Paths.get("src/resources/exemple.txt")).toList();
        List<String> lines = Files.lines(Paths.get("src/resources/fileToProcess.txt")).toList();
        long startTime = System.currentTimeMillis();


        process(lines);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Execution time: " + executionTime + " milliseconds");

    }

    private static void process(List<String> lines) {
        long[] seeds = Arrays.stream(
                        lines.get(0).split("\\s+"))
                .skip(1) // skip the label
                .mapToLong(Long::parseLong)
                .toArray();



        Map<String, List<long[]>> map = parseMaps(lines);
        Map<String, List<long[]>> conversionMap = convertMapToConvertionMap(map);

        findLowestLocationNumberAndPrintIt(seeds, conversionMap);

        findLowestLocationNumberInRangeAndPrintIt(seeds, conversionMap);
    }

    private static void findLowestLocationNumberInRangeAndPrintIt(long[] seeds, Map<String, List<long[]>> conversionMap) {
        long seedConverted;
        long smallestLocation = Long.MAX_VALUE;
        long smallestLocationSeed = -1;
        int i = 0;
        int j = 1;
        while (j < seeds.length) {
            long seed = seeds[i];
            while (seed < seeds[i] + seeds[j]) {
                seedConverted = endToEndConvertion(conversionMap, seed);

                if (seedConverted < smallestLocation) {
                    smallestLocation = seedConverted;
                    smallestLocationSeed = seed;

                }
                seed++;
            }

            i+=2;
            j+=2;
        }

        System.out.println("smallest location in range : " + smallestLocation);
        System.out.println("seed of the smallest location in range : " + smallestLocationSeed);
    }

    private static void findLowestLocationNumberAndPrintIt(long[] seeds, Map<String, List<long[]>> conversionMap) {
        long seedConverted;
        long smallestLocation = Long.MAX_VALUE;
        long smallestLocationSeed = -1;

        for (long seed : seeds) {
            seedConverted = endToEndConvertion(conversionMap, seed);

            if (seedConverted < smallestLocation) {
                smallestLocation = seedConverted;
                smallestLocationSeed = seed;
            }
        }
        System.out.println("smallest location : " + smallestLocation);
        System.out.println("seed of the smallest location : " + smallestLocationSeed);
    }

    static long endToEndConvertion(Map<String, List<long[]>> conversionMap, long seed) {
        MapName[] mapNames = MapName.values();

        long convertedValue = seed;
        for (MapName mapName : mapNames) {
            List<long[]> conversion = conversionMap.get(mapName.getName());
            if (conversion != null) {
                convertedValue = getSimpleConversion(convertedValue, conversion);
            }
        }
        return convertedValue;
    }

    static Map<String, List<long[]>> convertMapToConvertionMap(Map<String, List<long[]>> map) {

        Map<String, List<long[]>> convertedMap = new HashMap<>();

        map.forEach((mapName, inputMapList) -> {
            List<long[]> convertedList = new ArrayList<>();
            inputMapList.forEach(lineArray -> {
                long[] convertedArray = new long[3];
                convertedArray[0] = lineArray[START_SRC];
                convertedArray[1] = lineArray[START_SRC] + lineArray[RANGE] - 1;
                convertedArray[2] = lineArray[START_DEST] - lineArray[START_SRC];
                convertedList.add(convertedArray);
            });
            convertedList.sort(Comparator.comparingLong(number -> number[0]));
            convertedMap.put(mapName, convertedList);
        });

        return convertedMap;
    }

    public static Map<String, List<long[]>> parseMaps(List<String> lines) {
        Map<String, List<long[]>> parsedMap = new HashMap<>();
        List<long[]> valueMap = null;

        for (String line : lines) {
            String trimmedLine = line.trim();
            if (!trimmedLine.isEmpty() && !trimmedLine.startsWith("seeds:")) {

                if (trimmedLine.endsWith("map:")) {
                    valueMap = new ArrayList<>();
                    parsedMap.put(trimmedLine.replaceAll(" map:", ""), valueMap);
                } else if (valueMap != null) {
                    long[] values = extractLongArray(line);
                    valueMap.add(values);
                }
            }
        }

        return parsedMap;
    }

    static long[] extractLongArray(String line) {
        return Arrays.stream(line.split("\\s+"))
                .mapToLong(Long::parseLong)
                .toArray();
    }

    public static long getSimpleConversion(long valueToConvert, List<long[]> conversionMap) {
        long[] lineOfConvertionToUse = null;

        int index = getAproxIndexByBinarySearch(valueToConvert, conversionMap);

        if (index >= 0 && index < conversionMap.size()) {
            if (valueToConvert >= conversionMap.get(index)[0] && valueToConvert <= conversionMap.get(index)[1]) {
                lineOfConvertionToUse = conversionMap.get(index);
            }
        }
        return lineOfConvertionToUse != null ? valueToConvert + lineOfConvertionToUse[2] : valueToConvert;
    }

    public static int getAproxIndexByBinarySearch(long valueToConvert, List<long[]> conversionMap) {
        int start = 0;
        int end = conversionMap.size() - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            long midValue = conversionMap.get(mid)[0];

            if (midValue < valueToConvert) {
                start = mid + 1;
            } else if (midValue > valueToConvert) {
                end = mid - 1;
            } else {
                return mid;
            }
        }

        return start - 1;
    }
}


