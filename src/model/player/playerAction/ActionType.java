package model.player.playerAction;

import exceptions.IllegalActionException;
import model.board.Board;

public interface ActionType {
	abstract void takeAction(Board board) throws IllegalActionException;

	abstract Boolean validAction(Board board) throws IllegalActionException;
}
