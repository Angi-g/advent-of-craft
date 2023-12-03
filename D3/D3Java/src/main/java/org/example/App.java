package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
/*
        String content = "467..114..\n" +
                "...*......\n" +
                "..35..633.\n" +
                "......#...\n" +
                "617*......\n" +
                ".....+.58.\n" +
                "..592.....\n" +
                "......755.\n" +
                "...$.*....\n" +
                ".664.598..";*/
        String content = new String(Files.readAllBytes(Paths.get("src/ressources/fileToProcess.txt")));


        List<Number> numbersList = new ArrayList<>();
        List<int[]> symboleList = new ArrayList<>();
        int totalValueTouchBySymboles = 0;
        Scanner scanner = new Scanner(content);
        int index = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            numbersList.addAll(getMapedNumberInOneLine(line, index));
            symboleList.addAll(getMapedSymboleInLine(line, index));
            index++;
        }

        for (int[] ints : symboleList) {
            totalValueTouchBySymboles += valuesTouchedBySymbol(ints, numbersList);
        }
        System.out.println( "total value = " + totalValueTouchBySymboles);
    }

    static Integer valuesTouchedBySymbol(final int[] symbols, List<Number> numbersList) {

        Integer totalValue = 0;
        int indexOfSymbol = symbols[0];
        int coloneOfSymbol = symbols[1];
        totalValue += valuesOnTopOrBottomOfSymbol(coloneOfSymbol,
                numbersList.stream().filter(n -> n.indexLine == (indexOfSymbol - 1)).toList());
        totalValue += valueNextToSymbole(coloneOfSymbol,
                numbersList.stream().filter(n -> n.indexLine == indexOfSymbol).toList());
        totalValue += valuesOnTopOrBottomOfSymbol(coloneOfSymbol,
                numbersList.stream().filter(n -> n.indexLine == (indexOfSymbol + 1)).toList());

        return totalValue;
    }
    static Integer valuesOnTopOrBottomOfSymbol(int coloneOfSymbole, List<Number> numberList) {
        Integer value = 0;
        for (Number number : numberList) {
            if (number.start <= coloneOfSymbole + 1
                    && number.end >= coloneOfSymbole - 1) {
                value += number.value;
            }
            ;
        }
        return value;
    }
    static Integer valueNextToSymbole(int coloneOfSymbole, List<Number> numberList) {
        Integer value = 0;
        for (Number number : numberList) {
            if (number.start == coloneOfSymbole + 1
                    || number.end == coloneOfSymbole - 1) {
                value += number.value;
            }
            ;
        }
        return value;
    }

    static List<int[]> getMapedSymboleInLine(String line, int indexLine) {
        Pattern pattern = Pattern.compile("[^0-9^.]");
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
