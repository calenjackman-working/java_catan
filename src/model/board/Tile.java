package model.board;

import java.util.ArrayList;
import java.util.List;

import model.board.pieces.Robber;
import model.enums.ResourceType;

public class Tile {
	private Integer row, column;
	private List<CatanNode> nodes;
	private ResourceType resourceType;
	private Integer rollNumber;
	private Robber robber;

	public Tile(Integer row, Integer column) {
		this.row = row;
		this.column = column;
		this.nodes = new ArrayList<>();
		this.resourceType = null;
		this.rollNumber = null;
		this.robber = null;
	}

	public List<CatanNode> getNodes() {
		return nodes;
	}

	public Boolean addNode(CatanNode n) {
		if (this.nodes.contains(n)) {
			return false;
		}

		this.nodes.add(n);
		return true;
	}

	public Boolean removeNode(CatanNode n) {
		if (!(this.nodes.contains(n))) {
			return false;
		}

		this.nodes.remove(n);
		return true;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public void setRollNumber(Integer rollNumber) {
		this.rollNumber = rollNumber;
	}

	public Integer getRollNumber() {
		return rollNumber;
	}

	public Integer getRow() {
		return row;
	}

	public Integer getColumn() {
		return column;
	}

	public Robber getRobber() {
		return robber;
	}

	public void setRobber(Robber robber) {
		this.robber = robber;
	}
}
