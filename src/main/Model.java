package main;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static final int FIELD_WIDTH = 4;
    public Tile[][] gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
    protected int score;
    protected int maxTile;

    public Model() {
        resetGameTiles();
        score = 0;
        maxTile = 0;
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public boolean canMove() {
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[0].length; j++) {
                if (gameTiles[i][j].value == 0)
                    return true;
                if (i != 0 && gameTiles[i - 1][j].value == gameTiles[i][j].value)
                    return true;
                if (j != 0 && gameTiles[i][j - 1].value == gameTiles[i][j].value)
                    return true;
            }
        }
        return false;
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean isFieldChanged = false;

        int[] tileValues = new int[4];
        for (int i = 0; i < tileValues.length; i++) {
            tileValues[i] = tiles[i].value;
        }

        int count = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].value != 0) {
                tiles[count++].value = tiles[i].value;
            }
        }
        while (count < tiles.length) {
            tiles[count++].value = 0;
        }

        for (int i = 0; i < tiles.length; i++) {
            if (!(tileValues[i] == tiles[i].value)) {
                isFieldChanged = true;
            }
        }
        return isFieldChanged;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean isTilesMerged = false;
        for (int i = 0; i < tiles.length - 1; i++) {
            if (tiles[i].value == tiles[i + 1].value && tiles[i].value != 0) {
                tiles[i].value = tiles[i].value * 2;
                tiles[i + 1].value = 0;
                compressTiles(tiles);
                score += tiles[i].value;
                if (tiles[i].value > maxTile) {
                    maxTile = tiles[i].value;
                }
                isTilesMerged = true;
            }
        }
        return isTilesMerged;
    }

    public void left() {
        boolean isFieldChanged = false;
        for (int i = 0; i < gameTiles.length; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
                isFieldChanged = true;
            }
        }
        if (isFieldChanged & !getEmptyTiles().isEmpty()) {
            addTile();
        }
    }

    public void down() {
        rotation();
        boolean isFieldChanged = false;
        for (int i = 0; i < gameTiles.length; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
                isFieldChanged = true;
            }
        }
        if (isFieldChanged & !getEmptyTiles().isEmpty()) {
            addTile();
        }
        rotation();
        rotation();
        rotation();
    }

    public void right() {
        rotation();
        rotation();
        boolean isFieldChanged = false;
        for (int i = 0; i < gameTiles.length; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
                isFieldChanged = true;
            }
        }
        if (isFieldChanged & !getEmptyTiles().isEmpty()) {
            addTile();
        }
        rotation();
        rotation();
    }

    public void up() {
        rotation();
        rotation();
        rotation();
        boolean isFieldChanged = false;
        for (int i = 0; i < gameTiles.length; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
                isFieldChanged = true;
            }
        }
        if (isFieldChanged & !getEmptyTiles().isEmpty()) {
            addTile();
        }
        rotation();
    }

    public void rotation() {
        Tile[][] rotatedGameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                rotatedGameTiles[i][j] = gameTiles[FIELD_WIDTH - 1 - j][i];
            }
        }
        gameTiles = rotatedGameTiles;
    }

    private void addTile() {
        if (!getEmptyTiles().isEmpty()) {
            getEmptyTiles().get((int) (Math.random() * getEmptyTiles().size())).value = (Math.random() < 0.9) ? 2 : 4;
        }
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> tileList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameTiles[i][j].value == 0) {
                    tileList.add(gameTiles[i][j]);
                }
            }
        }
        return tileList;
    }

    public void resetGameTiles() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }
}
