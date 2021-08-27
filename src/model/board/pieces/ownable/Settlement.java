package model.board.pieces.ownable;

import model.board.CatanNode;
import model.enums.ResourceType;
import model.enums.SettlementStage;
import model.player.Player;

public class Settlement extends Ownable {
	private CatanNode node;
	private SettlementStage settlementStage;

	public Settlement(Player player, CatanNode node) {
		super(player, 1);
		this.node = node;
		this.settlementStage = SettlementStage.SETTLEMENT;
	}

	public CatanNode getNode() {
		return node;
	}

	public SettlementStage getSettlementStage() {
		return settlementStage;
	}

	@Override
	protected void setItemCost() {
		this.cost.put(ResourceType.BRICK, 1);
		this.cost.put(ResourceType.LUMBER, 1);
		this.cost.put(ResourceType.ORE, 0);
		this.cost.put(ResourceType.GRAIN, 1);
		this.cost.put(ResourceType.WOOL, 1);
	}
}
