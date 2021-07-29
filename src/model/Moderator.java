package model;

import java.util.ArrayList;
import java.util.List;
import model.board.Board;
import model.player.Player;

public class Moderator
{
	private Board board;
	private List<Player> players;
	private Integer turnNumber;

	public Moderator(List<Player> players)
	{
		this.board = new Board();
		this.players = players;
		this.turnNumber = 0;

		this.playGame();
	}

	private void playGame()
	{

	}

	public Board getBoard()
	{
		return board;
	}

	public List<Player> getPlayers()
	{
		return players;
	}

	public Integer getTurnNumber()
	{
		return turnNumber;
	}
}