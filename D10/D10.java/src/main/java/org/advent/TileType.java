package org.advent;

public enum TileType {
    NORTH_SOUTH('|'),
    EAST_WEST('-'),
    NORTH_EAST('L'),
    NORTH_WEST('J'),
    SOUTH_WEST('7'),
    SOUTH_EAST('F'),
    GROUND('.'),
    START('S');

    private final char tileType;
    TileType(char tileType) {
        this.tileType = tileType;
    }

    public char getTileType() {
        return tileType;
    }
    public static TileType getTileType(char tileChar) {
        switch (tileChar) {
            case '|':
                return TileType.NORTH_SOUTH;
            case '-':
                return TileType.EAST_WEST;
            case 'L':
                return TileType.NORTH_EAST;
            case 'J':
                return TileType.NORTH_WEST;
            case '7':
                return TileType.SOUTH_WEST;
            case 'F':
                return TileType.SOUTH_EAST;
            case '.':
                return TileType.GROUND;
            case 'S':
                return TileType.START;
            default:
                throw new IllegalArgumentException("ouaich, vérifie t map, ce char n'existe pas dans tes input possible" + tileChar);
        }
    }
}
