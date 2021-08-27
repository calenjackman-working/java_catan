package model.board.pieces.ownable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.board.CatanNode;
import model.enums.ResourceType;
import model.player.Player;

public class Road extends Ownable {
	private List<CatanNode> nodes;

	public Road(Player owner, CatanNode startNode, CatanNode endNode) {
		super(owner, 0);
		this.nodes = new ArrayList<>(Arrays.asList(startNode, endNode));
	}

	public List<CatanNode> getNodes() {
		return nodes;
	}

	@Override
	protected void setItemCost() {
		this.cost.put(ResourceType.BRICK, 1);
		this.cost.put(ResourceType.LUMBER, 1);
		this.cost.put(ResourceType.ORE, 0);
		this.cost.put(ResourceType.GRAIN, 0);
		this.cost.put(ResourceType.WOOL, 0);
	}
}
