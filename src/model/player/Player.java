package model.player;

import java.util.List;

import exceptions.IllegalActionException;
import model.board.Board;
import model.board.pieces.ownable.Road;
import model.board.pieces.ownable.Settlement;
import model.player.playerAction.ActionType;
import model.player.playerAction.PlayerAction;

public class Player {
	private Board board;
	private String playerName;
	private PlayerAction playerAction;

	public Player(Board b, String name) {
		this.board = b;
		this.playerName = name;
		this.playerAction = null;
	}

	public void setPlayerAction(ActionType at) {
		if (this.playerAction != null) {
			playerAction.setActionType(at);
		} else {
			this.playerAction = new PlayerAction(at);
		}
	}

	public void executePlayerAction() throws IllegalActionException {
		playerAction.takeAction(board);
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
		return board.getSettlements(this);
	}

	public List<Road> getRoads() {
		return board.getRoads(this);
	}

	@Override
	public String toString() {
		String msg = "Name: " + this.playerName;
		return msg;
	}
}
