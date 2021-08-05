package model.board;

import java.util.ArrayList;
import java.util.List;
import exceptions.IllegalSettlementAdditionException;
import model.board.ownable.Settlement;

public class Node {
	private Integer row, column;
	private List<Node> adjacentNodes;
	private List<Tile> tiles;
	private Settlement settlement;

	public Node(Integer row, Integer column) {
		this.row = row;
		this.column = column;
		this.adjacentNodes = new ArrayList<>();
		this.tiles = new ArrayList<>();
		this.settlement = null;
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

	public void setSettlement(Settlement settlement) throws IllegalSettlementAdditionException {
		if (this.settlement != null) {
			throw new IllegalSettlementAdditionException("This node is already assigned a settlement with "
					+ settlement.getOwner().toString() + " as the owner");
		} else {
			this.settlement = settlement;
		}
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public List<Node> getAdjacentNodes() {
		return adjacentNodes;
	}

	public void addAdjacentNode(Node n) {
		if (this.adjacentNodes.contains(n)) {
			return;
		}

		this.adjacentNodes.add(n);
	}

	public void removeAdjacentNode(Node n) {
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
