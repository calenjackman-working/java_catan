package model.player.playerAction;

import exceptions.IllegalActionException;
import model.board.Board;

public class PlayerAction {
	private ActionType actionType;

	public PlayerAction(ActionType at) {
		this.actionType = at;
	}

	public void setActionType(ActionType at) {
		this.actionType = at;
	}

	public void takeAction(Board b) throws IllegalActionException {
		actionType.takeAction(b);
	}
}
