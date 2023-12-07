package org.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        //List<String> lines = Files.lines(Paths.get("src/resources/exemple.txt")).toList();
        List<String> lines = Files.lines(Paths.get("src/resources/fileToProcess.txt")).toList();

        long[] times = getLongArray(lines.get(0));
        long[] distances = getLongArray(lines.get(1));

        long total = 1;
        for (int i = 0; i < times.length; i++) {
            total = total * getNumberOfWinWays(times[i], getMaxTimePush(times[i]), distances[i]);
        }

        long oneTime = getNumber(lines.get(0));
        long oneDistance = getNumber(lines.get(1));
        long oneTotal = getNumberOfWinWays(oneTime, getMaxTimePush(oneTime), oneDistance);
        System.out.println("total = " + total);
        System.out.println("oneTotal = " + oneTotal);
    }

    private static long getNumber(String line) {
        return Long.parseLong(line.replaceAll("\\D", ""));
    }

    static long getNumberOfWinWays(long time, long maxTimePush, long distanceRecord) {
        long timePush = maxTimePush - 1;
        long distance = maxTimePush * (time - maxTimePush);
        long numberOfWaysToWin = 0;
        while (distance > distanceRecord) {
            distance = timePush * (time - timePush);
            if (distance > distanceRecord) {
                numberOfWaysToWin++;
            }
            timePush--;
        }

        if (time % 2 > 0) {
            return numberOfWaysToWin * 2;
        } else {
            return numberOfWaysToWin * 2 + 1;
        }

    }

    static long getMaxTimePush(long time) {
        if (time % 2 > 0) {
            return (time + 1) / 2;
        } else {
            return time / 2;
        }
        // could return time % 2 != 0 ? (time + 1) / 2 : time / 2;
        // but it's ugly and not readable
    }

    static long[] getLongArray(String line) {
        line = line.replaceAll("\\w+:\\s+", "");
        return Arrays.stream(line.trim().split("\\s+")).mapToLong(Long::parseLong).toArray();
    }
}
