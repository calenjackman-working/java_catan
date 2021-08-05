package model.moderator.playerAction;

import exceptions.IllegalActionException;
import model.board.Board;

public interface ActionType {
	public Board takeAction(Board b) throws IllegalActionException;
}
