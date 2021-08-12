package model.board.pieces.ownable;

import model.player.Player;

public abstract class Ownable {
	protected Player owner;
	private Integer victoryPoints;

	public Ownable(Player player, Integer victoryPoints) {
		this.owner = player;
		this.victoryPoints = victoryPoints;
	}

	public Player getOwner() {
		return owner;
	}

	public Integer getVictoryPoints() {
		return victoryPoints;
	}
}
