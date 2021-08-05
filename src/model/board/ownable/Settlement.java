package model.board.ownable;

import exceptions.IllegalSettlementAdditionException;
import model.board.Board;
import model.board.Node;
import model.enums.SettlementStage;
import model.player.Player;

public class Settlement extends Ownable {
	private Node node;
	private Player player;
	private SettlementStage settlementStage;

	public Settlement(Board b, Node n, Player p) throws IllegalSettlementAdditionException {
		super(p, 1);
		this.node = n;
		this.settlementStage = SettlementStage.SETTLEMENT;

		try {
			this.node.setSettlement(this);
			this.player.addSettlement(this);
		} catch (IllegalSettlementAdditionException e) {
			throw e;
		}
	}

	public Node getNode() {
		return node;
	}

}
