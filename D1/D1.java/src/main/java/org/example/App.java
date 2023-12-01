package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("src/resources/fileToProcess2.txt")));
        content = replaceCharNumberByDigit(content);
        List<Integer> nmbr = extractAllNumberInMultipleLines(content);
        int finalValue = calculateFinalValue(nmbr);
        System.out.println(finalValue);

    }

    public static String replaceCharNumberByDigit(String content) {

        Map<String, String> number = new HashMap<>();
        number.put("one", "one1ne");
        number.put("two", "two2wo");
        number.put("three", "three3hree");
        number.put("four", "four4our");
        number.put("five", "five5ive");
        number.put("six", "six6ix");
        number.put("seven", "seven7even");
        number.put("eight", "eight8ight");
        number.put("nine", "nine9ine");

        for (Map.Entry<String, String> theNumber : number.entrySet()) {

            content = content.replaceAll(theNumber.getKey(), theNumber.getValue());
        }
        return content;
    }

    public static Integer extractNumberInline(String line) {
        Pattern digitRegex = Pattern.compile("\\d");
        // String reformatedLine = replaceCharNumberByDigit(line);
        Matcher digitMatcher = digitRegex.matcher(line);
        StringBuilder foundedDigit = new StringBuilder();

        while (digitMatcher.find()) {
            foundedDigit.append(digitMatcher.group());
        }
        if (!"".contentEquals(foundedDigit)) {
            String foundedDigitString = foundedDigit.toString();
            String numbertoReturn = foundedDigitString.charAt(0) + foundedDigitString.substring(foundedDigitString.length() - 1);
            return Integer.parseInt(numbertoReturn);
        }
        return 0;
    }

    public static List<Integer> extractAllNumberInMultipleLines(String content) {
        Scanner scanner = new Scanner(content);
        List<Integer> integerList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            integerList.add(extractNumberInline(scanner.nextLine()));
        }

        return integerList;
    }

    public static int calculateFinalValue(List<Integer> integerList) {
        return integerList.stream().mapToInt(Integer::intValue).sum();
    }

}
