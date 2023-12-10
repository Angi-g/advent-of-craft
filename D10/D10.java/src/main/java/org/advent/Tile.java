package org.advent;

public class Tile {
    private final TileType tileType;
    int line;
    int column;

    public Tile(TileType tileType, int line, int column ) {
        this.tileType = tileType;
        this.line = line;
        this.column = column;
    }

    public TileType getTileType() {
        return tileType;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "tileType=" + tileType +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
