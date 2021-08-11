package model.board.ownable;

import model.board.CatanNode;
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
}
