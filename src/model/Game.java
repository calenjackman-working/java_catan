package model;

import java.util.List;
import exceptions.PlayerNameTakenException;
import exceptions.TooManyPlayersException;
import model.board.Board;
import model.enums.GamePhase;
import model.moderator.Moderator;
import model.player.Player;

public class Game {
	private Board board;
	private List<Player> players;
	private Moderator mod;
	private GamePhase gamePhase;

	public Game(List<Player> players) {
		this.board = new Board();
		this.players = players;
		this.mod = new Moderator(this);
		this.gamePhase = GamePhase.SETUP;
	}

	public Board getBoard() {
		return board;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Moderator getMod() {
		return mod;
	}

	public GamePhase getGamePhase() {
		return gamePhase;
	}

	public void addPlayer(String name) throws TooManyPlayersException, PlayerNameTakenException {
		// if (gameIsFull()) {
		// throw new TooManyPlayersException(
		// "There are already 4 players in this game. Please remove a player if you
		// would like to add another.");
		// } else if (playerNameTaken(name)) {
		// throw new PlayerNameTakenException(
		// name + " is already being used as a name for another player. Please select
		// another name.");
		// } else {
		// this.players.add(new Player(this.board, name));
		// }
	}

	public void removePlayer(String name) {
	}

	public boolean gameIsFull() {
		return this.players.size() == 4;
	}

	private Boolean playerNameTaken(String name) {
		// for (Player player : players) {
		// if (player.getPlayerName() == name) {
		// return true;
		// }
		// }
		return null;
	}
}
