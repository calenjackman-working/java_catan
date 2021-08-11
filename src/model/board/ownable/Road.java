package model.board.ownable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.board.CatanNode;
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
}
