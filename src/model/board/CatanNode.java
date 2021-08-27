package model.board;

import java.util.ArrayList;
import java.util.List;
import exceptions.IllegalSettlementAdditionException;
import model.board.pieces.ownable.Road;
import model.board.pieces.ownable.Settlement;

public class CatanNode {
    private Integer row, column;
    private List<CatanNode> adjacentNodes;
    private List<Tile> tiles;
    private Settlement settlement;
    private List<Road> roads;

    public CatanNode(Integer row, Integer column) {
        this.row = row;
        this.column = column;
        this.adjacentNodes = new ArrayList<>();
        this.tiles = new ArrayList<>();
        this.settlement = null;
        this.roads = new ArrayList<>();
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public Settlement getSettlement() {
        return settlement;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<CatanNode> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setSettlement(Settlement settlement) throws IllegalSettlementAdditionException {
        this.settlement = settlement;
    }

    public void addRoad(Road road) {
        this.roads.add(road);
    }

    public void addAdjacentNode(CatanNode n) {
        if (this.adjacentNodes.contains(n)) {
            return;
        }

        this.adjacentNodes.add(n);
    }

    public void removeAdjacentNode(CatanNode n) {
        if (!(this.adjacentNodes.contains(n))) {
            return;
        }

        this.adjacentNodes.remove(n);
    }

    public Boolean addTile(Tile t) {
        if (this.tiles.contains(t)) {
            return false;
        }

        this.tiles.add(t);
        return true;
    }

    @Override
    public String toString() {
        String msg = "Node @ Row: " + getRow() + " Col: " + getColumn();
        return msg;
    }
}
