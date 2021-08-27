package model.board.pieces;

import model.board.Tile;

public class Robber {
    private Tile tile;

    public Robber(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
