package org.advent;

public enum Directions {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public static Directions getFrom(Directions directions) {
        switch (directions) {
            case NORTH:
                return Directions.SOUTH;
            case SOUTH:
                return Directions.NORTH;
            case EAST:
                return Directions.WEST;
            case WEST:
                return Directions.EAST;
        }
        return null;
    }
}
