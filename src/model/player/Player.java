package model.player;

import java.util.List;
import exceptions.IllegalSettlementAdditionException;
import model.board.Board;
import model.board.ownable.Settlement;
import model.moderator.playerAction.ActionType;
import model.moderator.playerAction.PlayerAction;

public class Player {
	private String playerName;
	private Board board;
	private PlayerAction playerAction;
	private List<Settlement> settlements;

	public Player(Board b, String name) {
		this.board = b;
		this.playerName = name;
	}

	public void setPlayerAction(ActionType at) {
		if (this.playerAction != null) {
			playerAction.setActionType(at);
		} else {
			this.playerAction = new PlayerAction(at);
		}
	}

	public void executePlayerAction() {
		playerAction.takeAction(board);
	}

	public void addSettlement(Settlement s) throws IllegalSettlementAdditionException {
		if (!(this.settlements.contains(s))) {
			this.settlements.add(s);
		} else {
			throw new IllegalSettlementAdditionException("Player already owns Settlement at " + s.getNode().toString());
		}
	}

	public Board getBoard() {
		return board;
	}

	public PlayerAction getPlayerAction() {
		return playerAction;
	}

	public String getPlayerName() {
		return playerName;
	}

	public List<Settlement> getSettlements() {
		return settlements;
	}

	@Override
	public String toString() {
		String msg = "Name: " + this.playerName;
		return msg;
	}
}
