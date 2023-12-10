package org.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class App {
    public static void main(String[] args) throws IOException {
        // List<String> lines = Files.lines(Paths.get("src/resources/exemple.txt")).toList();
        List<String> lines = Files.lines(Paths.get("src/resources/fileToProcess.txt")).toList();
        char[] path = lines.get(0).toCharArray();

        long startTime = System.currentTimeMillis();
        Map<String, String[]> map = lines.stream()
                .skip(2)
                .map(line -> line.split(" = "))
                .collect(
                        HashMap::new,
                        (m, parts) -> m.put(parts[0], parts[1].replaceAll("[()]", "").split(", ")),
                        HashMap::putAll
                );

        // part 1
        int numberOfExecution = getNumberOfExecution(map, path);
        // part 2
        List<String> starts = map.keySet()
                .stream()
                .filter(key -> key.endsWith("A"))
                .toList();

        //  bruteforce is bad, use your brain è.é
        //  Long multipleNumberOfExecution = getMultipleNumberOfExecution(map, path);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("numberOfExecution = " + numberOfExecution);
        System.out.println("Execution time: " + executionTime + " milliseconds");
    }


    private static int getNumberOfExecution(Map<String, String[]> map, char[] path) {
        String attendedEnd = "ZZZ";
        String current = "AAA";
        int numberOfExecution = 0;
        int index = 0;
        while (!Objects.equals(current, attendedEnd)) {
            String[] getCurrent = map.get(current);
            current = path[index] == 'L' ? getCurrent[0] : getCurrent[1];
            numberOfExecution++;
            index = (index + 1) % path.length;
        }
        return numberOfExecution;
    }

}
