package org.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class App 
{
    public static void main( String[] args ) throws IOException {
       // List<String> lines = Files.lines(Paths.get("src/resources/exemple.txt")).toList();
        List<String> lines = Files.lines(Paths.get("src/resources/fileToProcess.txt")).toList();

        List<String> handsList = new ArrayList<>();
        List<Integer> bids = new ArrayList<>();

        for(String line : lines) {
            String[] score = line.split(" ");
            handsList.add(score[0]);
            bids.add(Integer.parseInt(score[1]));
        }

        List<Hand> hands = new ArrayList<>();
        for (int i = 0; i < handsList.size(); i++) {
            hands.add(new Hand(handsList.get(i), bids.get(i), getHandType(handsList.get(i))));
        }

        hands.sort(new HandComparator());

        int totalWinnings = 0;
        for (int i = 0; i < hands.size(); i++) {
            totalWinnings += hands.get(i).bid() * (i + 1);
            System.out.println(hands.get(i).toString());
        }

        System.out.println("\nTotal Winnings: " + totalWinnings);


    }

    private static HandType getHandType(String hand) {

        if (isFiveOfAKind(hand)) {
            return HandType.FIVE_OF_A_KIND;
        } else if (isFourOfAKind(hand)) {
            return HandType.FOUR_OF_A_KIND;
        } else if (isFullHouse(hand)) {
            return HandType.FULL_HOUSE;
        } else if (isThreeOfAKind(hand)) {
            return HandType.THREE_OF_A_KIND;
        } else if (isTwoPair(hand)) {
            return HandType.TWO_PAIR;
        } else if (isOnePair(hand)) {
            return HandType.ONE_PAIR;
        } else {
            return HandType.HIGH_CARD;
        }
    }

    private static boolean isFiveOfAKind(String hand) {
        return hasCount(hand, 5);
    }

    private static boolean isFourOfAKind(String hand) {
        return hasCount(hand, 4);
    }

    private static boolean isFullHouse(String hand) {
        if (hand.contains("J")) {
            if (hasCount(hand, 3)) {
                return countPairs(hand) == 2;
            }
        } else{
            return hasCount(hand, 3) && hasCount(hand, 2);
        }
        return false;
    }

    private static boolean isThreeOfAKind(String hand) {
        return hasCount(hand, 3);
    }

    private static boolean isTwoPair(String hand) {
        return countPairs(hand) == 2;
    }

    private static boolean isOnePair(String hand) {
        return countPairs(hand) == 1;
    }

    private static boolean hasCount(String hand, int count) {
        Map<Character, Integer> cardCounts = countOccurrences(hand);

        char maxCard = 0;
        for (char card : cardCounts.keySet()) {
            if (cardCounts.size() != 1 && card != 'J' && cardCounts.get(card) > cardCounts.getOrDefault(maxCard, 0)) {
                maxCard = card;
            }
        }
        if (cardCounts.size() != 1 && cardCounts.containsKey('J')) {
            int jCount = cardCounts.get('J');
            int newCount = cardCounts.get(maxCard) + jCount;
                cardCounts.put(maxCard, newCount);
        }
        return cardCounts.containsValue(count);
    }

    private static int countPairs(String hand) {
        Map<Character, Integer> cardCounts = countOccurrences(hand);
        int pairCount = 0;
        for (int count : cardCounts.values()) {
            if (count == 2) {
                pairCount++;
            }
        }
        if (pairCount == 0) {
            if(cardCounts.containsKey('J')) {
                if (cardCounts.get('J') == 1){
                    pairCount++;
                }
            }
        }
        return pairCount;
    }

    private static Map<Character, Integer> countOccurrences(String hand) {
        Map<Character, Integer> cardCounts = new HashMap<>();
        for (char card : hand.toCharArray()) {
            cardCounts.put(card, cardCounts.getOrDefault(card, 0) + 1);
        }

        return cardCounts;
    }

}

