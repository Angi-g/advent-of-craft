package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest
{
   @Test
   void matchUnvalideLine() {
      Game game = new Game(2, 10, 10, 10);
      Game gameToMatch = new Game(0, 1, 1, 1);

       Assertions.assertFalse(App.isMatch(game, gameToMatch));
   }

   @Test
   void matchValideLine() {
      Game gameToMatch = new Game(2, 10, 10, 10);
      Game game = new Game(0, 1, 1, 1);

       Assertions.assertTrue(App.isMatch(game, gameToMatch));
   }

   @Test
   void testCreateGame() {
      String game = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green";
      int expectedID = 1;
      int expetedGreen = 2;
      int expectedRed = 4;
      int expectedBlue = 6;
      Game gameTotest = App.createGame(game);

      Assertions.assertEquals(expectedID, gameTotest.ID );
      Assertions.assertEquals(expetedGreen, gameTotest.green);
      Assertions.assertEquals(expectedRed, gameTotest.red);
      Assertions.assertEquals(expectedBlue, gameTotest.blue);
   }

   @Test
   void testcalculateNumberOfMatch() {
      List<Game> games = App.createListOfGame("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green\n" +
              "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue\n" +
              "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red\n" +
              "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red\n" +
              "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green");
      Game gameToMatch = new Game(0, 13, 12, 14);
      Assertions.assertEquals(8, App.calculateNumberMatch(games, gameToMatch));
   }

   @Test
   void testCalculatePowerOfGame() {
      Game game = new Game(2, 4, 5, 6);
      int expectedPower = 120;

      Assertions.assertEquals(expectedPower, App.calculatePowerOfGame(game));
   }

   @Test
   void testSumOfPowersOfGame() {
      List<Game> games = App.createListOfGame("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green\n" +
              "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue\n" +
              "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red\n" +
              "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red\n" +
              "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green");
      int expectedSum = 2286;
      Assertions.assertEquals(expectedSum, App.sumOfPowersOfGame(games));
   }
}
