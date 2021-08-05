package model.moderator.playerAction;

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

	public Board takeAction(Board b) {
		try {
			return actionType.takeAction(b);
		} catch (IllegalActionException e) {
			// give user feedback
			return b;
		}
	}
}
