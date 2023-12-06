package org.advent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    void testParseMaps() {
        String inputString = """
                seed-to-soil map:
                50 98 2
                52 50 48
                               
                soil-to-fertilizer map:
                0 15 37
                37 52 2
                39 0 15
                """;
        List<String> inputList = Arrays.asList(inputString.split("\\n"));

        Map<String, List<long[]>> expectedMap = new HashMap<>();
        List<long[]> seedToSoilMap = Arrays.asList(
                // new long[]{0, 49, 0},
                new long[]{50, 98, 2},
                new long[]{52, 50, 48}
        );
        List<long[]> soilToFertilizerMap = Arrays.asList(
                new long[]{0, 15, 37},
                new long[]{37, 52, 2},
                new long[]{39, 0, 15}
        );
        expectedMap.put("seed-to-soil", seedToSoilMap);
        expectedMap.put("soil-to-fertilizer", soilToFertilizerMap);
        Map<String, List<long[]>> actualMap = App.parseMaps(inputList);

        Assertions.assertArrayEquals(expectedMap.get("seed-to-soil").get(0), actualMap.get("seed-to-soil").get(0));
        Assertions.assertArrayEquals(expectedMap.get("seed-to-soil").get(1), actualMap.get("seed-to-soil").get(1));
        Assertions.assertArrayEquals(expectedMap.get("soil-to-fertilizer").get(0), actualMap.get("soil-to-fertilizer").get(0));
        Assertions.assertArrayEquals(expectedMap.get("soil-to-fertilizer").get(1), actualMap.get("soil-to-fertilizer").get(1));
        Assertions.assertArrayEquals(expectedMap.get("soil-to-fertilizer").get(2), actualMap.get("soil-to-fertilizer").get(2));

    }

    @Test
    void testConvertMapToMapConvertion() {
        Map<String, List<long[]>> map = new HashMap<>();
        List<long[]> seedToSoilMap = Arrays.asList(
                // new long[]{0, 49, 0},
                new long[]{50, 98, 2},
                new long[]{52, 50, 48}
        );
        List<long[]> soilToFertilizerMap = Arrays.asList(
                new long[]{0, 15, 37},
                new long[]{37, 52, 2},
                new long[]{39, 0, 15}
        );
        map.put("seed-to-soil", seedToSoilMap);
        map.put("soil-to-fertilizer", soilToFertilizerMap);


        Map<String, List<long[]>> expectedMap = new HashMap<>();
        List<long[]> convertedSeedToSoilMap = Arrays.asList(
                new long[]{98, 99, -48},
                new long[]{50, 97, 2}
        );
        List<long[]> convertedSoilToFertilizerMap = Arrays.asList(
                new long[]{15, 51, -15},
                new long[]{52, 53, -15},
                new long[]{0, 14, 39}
        );
        expectedMap.put("seed-to-soil", convertedSeedToSoilMap);
        expectedMap.put("soil-to-fertilizer", convertedSoilToFertilizerMap);
        Map<String, List<long[]>> actualMap = App.convertMapToConvertionMap(map);
        Assertions.assertArrayEquals(expectedMap.get("seed-to-soil").get(0), actualMap.get("seed-to-soil").get(0));
        Assertions.assertArrayEquals(expectedMap.get("seed-to-soil").get(1), actualMap.get("seed-to-soil").get(1));
        Assertions.assertArrayEquals(expectedMap.get("soil-to-fertilizer").get(0), actualMap.get("soil-to-fertilizer").get(0));
        Assertions.assertArrayEquals(expectedMap.get("soil-to-fertilizer").get(1), actualMap.get("soil-to-fertilizer").get(1));
        Assertions.assertArrayEquals(expectedMap.get("soil-to-fertilizer").get(2), actualMap.get("soil-to-fertilizer").get(2));

    }

    @ParameterizedTest
    @CsvSource({"79,81", "13,13", "14,14", "55,57"})
    void testGetSimpleConvertion(long seedToConvert, long expectedNumberConverted) {
        /*
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48
         */
        List<long[]> convertedSeedToSoilMap = Arrays.asList(
                new long[]{98, 99, -48},
                new long[]{50, 97, 2}
        );
        long seedConverted = App.getSimpleConversion(seedToConvert, convertedSeedToSoilMap);
        Assertions.assertEquals(expectedNumberConverted, seedConverted);
    }

    @Test
    void testEndToEndConvertion() {
        Map<String, List<long[]>> conversionMap = new HashMap<>();
        conversionMap.put("seed-to-soil", Arrays.asList(
                new long[]{98, 99, -48},
                new long[]{50, 97, 2}
        ));
        conversionMap.put("soil-to-fertilizer", Arrays.asList(
                new long[]{15, 51, -15},
                new long[]{52, 53, -15},
                new long[]{0, 14, 39}
        ));
        conversionMap.put("fertilizer-to-water", Arrays.asList(
                new long[]{53, 60, -4},
                new long[]{11, 52, -11},
                new long[]{0, 6, 42},
                new long[]{7, 10, 50}
        ));

        conversionMap.put("water-to-light", Arrays.asList(
                new long[]{18, 24, 70},
                new long[]{25, 94, -7}
        ));
        conversionMap.put("light-to-temperature", Arrays.asList(
                new long[]{77, 99, -32},
                new long[]{45, 63, 36},
                new long[]{64, 76, 4}
        ));

        conversionMap.put("temperature-to-humidity", Arrays.asList(
                new long[]{69, 69, -69},
                new long[]{0, 68, 1}
        ));

        conversionMap.put("humidity-to-location", Arrays.asList(
                new long[]{56, 92, 4},
                new long[]{93, 96, -37}
        ));

        Assertions.assertEquals(82, App.endToEndConvertion(conversionMap, 79));
        Assertions.assertEquals(43, App.endToEndConvertion(conversionMap, 14));
        Assertions.assertEquals(86, App.endToEndConvertion(conversionMap, 55));
        Assertions.assertEquals(35, App.endToEndConvertion(conversionMap, 13));


    }
}
