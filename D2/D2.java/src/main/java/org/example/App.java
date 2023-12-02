package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main(String[] args ) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("src/ressources/fileToProcess.txt")));

        Game gameToMatch = new Game(0, 13, 12, 14);
        List<Game> games = createListOfGame(content);
        int count = calculateNumberMatch(games, gameToMatch);
        int sumOfPower = sumOfPowersOfGame(games);
        System.out.println("count of matching game : " + count
        + "\n sum of all power = " + sumOfPower);

    }


    static int sumOfPowersOfGame(List<Game> games ) {
        int sum = 0;
        for(Game game: games) {
            sum += calculatePowerOfGame(game);
        }
        return sum;
    }
    static int calculatePowerOfGame(Game game) {
        return game.red * game.blue * game.green;
    }

    static int calculateNumberMatch(List<Game> games, Game toMatch) {
        int count = 0;
        for (Game game : games) {
            if (isMatch(game, toMatch)) {
                count += game.ID;
            }
        }
        return count;
    }

    static boolean isMatch (Game game, Game toMatch) {
        return game.green <= toMatch.green
                && game.blue <= toMatch.blue
                && game.red <= toMatch.red;
    }

    static List<Game> createListOfGame(String content) {
        Scanner scanner = new Scanner(content);

        List<Game> games = new ArrayList<>();
        while (scanner.hasNextLine()) {
            games.add(createGame(scanner.nextLine()));
        }

        return games;
    }

    static Game createGame(String line) {
        String[] splitted = line.split(":");
        int ID;
        int green = 0;
        int red = 0;
        int blue = 0;

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(splitted[0]);

        matcher.find();
        ID = Integer.parseInt(matcher.group());

        pattern = Pattern.compile("(\\d+) (\\w+)");
        matcher = pattern.matcher(splitted[1]);
        while (matcher.find()) {
            int count = Integer.parseInt(matcher.group(1));
            String color = matcher.group(2);

            switch (color) {
                case "green" : {
                    if (count > green)
                        green = count;
                    break;
                }case "red" : {
                    if (count > red)
                        red = count;
                    break;
                }case "blue" : {
                    if (count > blue)
                        blue = count;
                    break;
                }
            }
        }

       return new Game(ID, green, red, blue);
    }
}
