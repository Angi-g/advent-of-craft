package org.advent;

record Hand(String hand, int bid, HandType handType) {
    // my first record, Yay !
    @Override
    public String toString() {
        return "Hand{" +
                "hand='" + hand + '\'' +
                ", bid=" + bid +
                ", handType=" + handType +
                '}';
    }
}