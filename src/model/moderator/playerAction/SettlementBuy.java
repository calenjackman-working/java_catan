package model.moderator.playerAction;

import exceptions.IllegalSettlementAdditionException;
import model.board.Board;
import model.board.Node;
import model.board.ownable.Settlement;
import model.player.Player;

public class SettlementBuy implements ActionType {
	private Node node;
	private Player player;

	public SettlementBuy(Player p, Node node) {
		this.node = node;
		this.player = p;
	}

	@Override
	public Board takeAction(Board b) throws IllegalSettlementAdditionException {
		try {
			Settlement newSettlement = new Settlement(b, this.node, this.player);
		} catch (IllegalSettlementAdditionException e) {
			// give error feedback to user
		}
		return null;
	}
}
