package org.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException {
       // List<String> lines = Files.lines(Paths.get("src/resources/exemple.txt")).collect(Collectors.toList());
        List<String> lines = Files.lines(Paths.get("src/resources/fileToProcess.txt")).collect(Collectors.toList());

        Tile startTile = null;
        for (int line = 0; line < lines.size(); line++) {
            int column = lines.get(line).indexOf('S');
            if (-1 != column) {
                startTile = new Tile(TileType.START, line, column);
            }
        }

        List<Tile> path = new LinkedList<>();
        path.add(startTile);
        Directions nextDirection = findFirstDirection(lines, startTile);
        Tile nextTile = getNextTileFromDirection(nextDirection, lines, startTile);
        while (!TileType.START.equals(nextTile.getTileType())) {
            path.add(nextTile);
            nextDirection = findNextDirection(nextTile, Directions.getFrom(nextDirection));
            nextTile = getNextTileFromDirection(nextDirection, lines, nextTile);
            System.out.println(nextTile.toString());
        }

        Tile middleTile = path.get(path.size() / 2);
        System.out.println(middleTile.toString() + ", index = " + path.size() / 2);
    }

    private static Directions findNextDirection(Tile nextTile, Directions from) {
        switch (nextTile.getTileType()) {
            case NORTH_SOUTH:
                if (Directions.NORTH.equals(from)) {
                    return Directions.SOUTH;
                } else {
                    return Directions.NORTH;
                }
            case EAST_WEST:
                if (Directions.EAST.equals(from)) {
                    return Directions.WEST;
                } else {
                    return Directions.EAST;
                }
            case NORTH_EAST:
                if (Directions.NORTH.equals(from)) {
                    return Directions.EAST;
                } else {
                    return Directions.NORTH;
                }
            case NORTH_WEST:
                if (Directions.NORTH.equals(from)) {
                    return Directions.WEST;
                } else {
                    return Directions.NORTH;
                }
            case SOUTH_WEST:
                if (Directions.SOUTH.equals(from)) {
                    return Directions.WEST;
                } else {
                    return Directions.SOUTH;
                }
            case SOUTH_EAST:
                if (Directions.SOUTH.equals(from)) {
                    return Directions.EAST;
                } else {
                    return Directions.SOUTH;
                }
            default:
                return null;
        }
    }

    private static Tile getNextTileFromDirection(Directions nextDirection, List<String> lines, Tile startTile) {
        int line;
        int column;
        switch (nextDirection) {
            case NORTH:
                line = startTile.line - 1;
                column = startTile.column;
                return new Tile(TileType.getTileType(lines.get(line).charAt(column)), line, column);
            case SOUTH:
                line = startTile.line + 1;
                column = startTile.column;
                return new Tile(TileType.getTileType(lines.get(line).charAt(column)), line, column);
            case EAST:
                line = startTile.line;
                column = startTile.column + 1;
                return new Tile(TileType.getTileType(lines.get(line).charAt(column)), line, column);
            case WEST:
                line = startTile.line;
                column = startTile.column - 1;
                return new Tile(TileType.getTileType(lines.get(line).charAt(column)), line, column);
        }
        return null;
    }

    static Directions findFirstDirection(List<String> lines, Tile startTile) {
        int line = startTile.getLine();
        int column = startTile.getColumn();
        char northTile = line != 0 ? lines.get(line - 1).charAt(column) : 0;
        char southTile = line != lines.size() ? lines.get(line + 1).charAt(column) : 0;
        char eastTile = startTile.column != lines.get(line).length() ? lines.get(line).charAt(column + 1): 0;
        char westTile = startTile.column != 0 ? lines.get(line).charAt(column - 1): 0;

        if (northTile != 0 && (TileType.NORTH_SOUTH.getTileType() == northTile
                || TileType.SOUTH_WEST.getTileType() == northTile
                || TileType.SOUTH_EAST.getTileType() == northTile)) {
            return Directions.NORTH;                                // line - 1, column
        } else if (southTile != 0 && (TileType.NORTH_SOUTH.getTileType() == southTile
                || TileType.SOUTH_WEST.getTileType() == southTile
                || TileType.SOUTH_EAST.getTileType() == southTile)) {
            return Directions.SOUTH;                                // line + 1, column
        } else if (eastTile != 0 && (TileType.EAST_WEST.getTileType() == eastTile
                || TileType.SOUTH_WEST.getTileType() == eastTile
                || TileType.NORTH_WEST.getTileType() == eastTile)) {
            return Directions.EAST;                                 // line, column + 1 );
        } else if (westTile != 0 && (TileType.NORTH_EAST.getTileType() == westTile
                || TileType.SOUTH_EAST.getTileType() == westTile
                || TileType.EAST_WEST.getTileType() == westTile)) {
            return Directions.WEST;                                // line, column - 1);
        }
        return null;
    }
}
