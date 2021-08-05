package model.moderator;

import java.util.List;

import exceptions.NoPlayersInGameException;
import exceptions.PlayerDoesNotExistException;
import exceptions.PlayerNameTakenException;
import exceptions.TooManyPlayersException;
import model.Game;

public class Moderator {
	private Game game;
	private Integer turnNumber;

	public Moderator(Game game) {
		this.game = game;
		this.turnNumber = 0;
	}

	public Game getGame() {
		return game;
	}

	public Integer getTurnNumber() {
		return turnNumber;
	}

	// TODO - Implement method
	public void addPlayer(String name) throws TooManyPlayersException, PlayerNameTakenException {
		if (this.game.gameIsFull()) {
			throw new TooManyPlayersException(
					"The game already has a full player list. Please remove a player before you attempt to add another.");
		}
	}

	// TODO - Implement method
	public void removePlayer(String name) throws PlayerDoesNotExistException, NoPlayersInGameException {
	}

	// TODO - Implement method
	public void removeAllPlayers(String name) throws PlayerDoesNotExistException, NoPlayersInGameException {
	}

	// TODO - Implement method
	public void addAllPlayers(List<String> playerNames) {
	}
}