package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        //penses à gérer les exeption non de Zeus !

        String content = new String(Files.readAllBytes(Paths.get("src/ressources/fileToProcess.txt")));

        List<Number> numbersList = new ArrayList<>();
        List<int[]> symbolsList = new ArrayList<>();
        List<int[]> starsList = new ArrayList<>();

        int totalValueTouchBySymboles = 0;
        int totalValueTouchByStars = 0;
        Scanner scanner = new Scanner(content);

        int index = 0;

        Pattern patternSymbols = Pattern.compile("[^0-9^.]");
        Pattern patternStars = Pattern.compile("[*]");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            numbersList.addAll(getMapedNumberInOneLine(line, index));
            symbolsList.addAll(getMapedSymboleInLine(line, index, patternSymbols));
            starsList.addAll(getMapedSymboleInLine(line, index, patternStars));
            index++;
        }

        for (int[] symbol : symbolsList) {
            totalValueTouchBySymboles += valuesTouchedBySymbol(symbol, numbersList);
        }

        for (int[] star : starsList) {
            totalValueTouchByStars += valuesTouchedByStar(star, numbersList);
        }

        System.out.println("total value touch by symbols= " + totalValueTouchBySymboles);
        System.out.println("total value touch by stars= " + totalValueTouchByStars);
    }

    static Integer valuesTouchedByStar(final int[] symbols, List<Number> numbersList) {

        List<Integer> totalValue = getListOfValueTouchByPattern(symbols, numbersList);

        if (totalValue.size() == 2) {
            return totalValue.get(0) * totalValue.get(1);
        } else {
            return 0;
        }
    }

    static Integer valuesTouchedBySymbol(final int[] symbols, List<Number> numbersList) {
        List<Integer> totalValue = getListOfValueTouchByPattern(symbols, numbersList);

        return totalValue.stream().mapToInt(Integer::intValue).sum();
    }


    static List<Integer> getListOfValueTouchByPattern(int[] symbols, List<Number> numbersList) {
        List<Integer> totalValue = new ArrayList<>();
        int indexOfSymbol = symbols[0];
        int coloneOfSymbol = symbols[1];

        totalValue.addAll(valuesOnTopOrBottomOfSymbol(coloneOfSymbol,
                numbersList.stream().filter(n -> n.indexLine == (indexOfSymbol - 1)).toList()));
        totalValue.addAll(valueNextToSymbole(coloneOfSymbol,
                numbersList.stream().filter(n -> n.indexLine == indexOfSymbol).toList()));
        totalValue.addAll(valuesOnTopOrBottomOfSymbol(coloneOfSymbol,
                numbersList.stream().filter(n -> n.indexLine == (indexOfSymbol + 1)).toList()));

        return totalValue;
    }
    static List<Integer> valuesOnTopOrBottomOfSymbol(int coloneOfSymbole, List<Number> numberList) {
        List<Integer> values = new ArrayList<>();
        for (Number number : numberList) {
            if (number.start <= coloneOfSymbole + 1
                    && number.end >= coloneOfSymbole - 1) {
                values.add(number.value);
            }
        }
        return values;
    }

    static List<Integer> valueNextToSymbole(int coloneOfSymbole, List<Number> numberList) {
        List<Integer> values = new ArrayList<>();
        for (Number number : numberList) {
            if (number.start == coloneOfSymbole + 1
                    || number.end == coloneOfSymbole - 1) {
                values.add(number.value);
            }
        }
        return values;
    }

    static List<int[]> getMapedSymboleInLine(String line, int indexLine, Pattern pattern) {
        Matcher matcher = pattern.matcher(line);

        List<int[]> symboleList = new ArrayList<>();
        while (matcher.find()) {
            symboleList.add(new int[]{indexLine, matcher.start()});
        }
        return symboleList;
    }


    static List<Number> getMapedNumberInOneLine(String line, int indexLine) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(line);

        int number;
        int start;
        int end;
        List<Number> numbersList = new ArrayList<>();
        while (matcher.find()) {
            number = Integer.parseInt(matcher.group());
            start = matcher.start();
            end = matcher.end() - 1;
            numbersList.add(new Number(number, indexLine, start, end));
        }
        return numbersList;
    }
}
