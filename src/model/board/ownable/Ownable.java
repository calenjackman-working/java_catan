package model.board.ownable;

import model.player.Player;

public abstract class Ownable {
	private Player owner;
	private Integer victoryPoints;

	public Ownable(Player p, Integer vp) {
		this.owner = p;
		this.victoryPoints = vp;
	}

	public Player getOwner() {
		return owner;
	}

	public Integer getVictoryPoints() {
		return victoryPoints;
	}
}
