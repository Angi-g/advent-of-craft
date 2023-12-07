package org.advent;

import java.util.Comparator;

class HandComparator implements Comparator<Hand> {
    @Override
    public int compare(Hand hand1, Hand hand2) {

        int typeComparaison = hand2.handType().ordinal() - hand1.handType().ordinal();
        if (typeComparaison != 0) {
            return typeComparaison;
        }

        for (int i = 0; i < hand1.hand().length(); i++) {
            char card1 = hand1.hand().charAt(i);
            char card2 = hand2.hand().charAt(i);

            int cardComparison = compareCards(card1, card2);
            if (cardComparison != 0) {
                return cardComparison;
            }
        }
        return 0;
    }

    private int compareCards(char card1, char card2) {
        return Integer.compare(getCardValue(card1), getCardValue(card2));
    }

    private static int getCardValue(char card) {
        //my first use of the new switch case of java, yay
        return switch (card) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 1; //part1 : case 'J' -> 11;
            case 'T' -> 10;
            default -> Character.getNumericValue(card);
        };
    }
}

