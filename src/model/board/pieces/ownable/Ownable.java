package model.board.pieces.ownable;

import model.player.Player;

import java.util.HashMap;

import model.enums.ResourceType;

public abstract class Ownable {
	protected Player owner;
	protected HashMap<ResourceType, Integer> cost;
	private Integer victoryPoints;

	public Ownable(Player player, Integer victoryPoints) {
		this.owner = player;
		this.cost = new HashMap<>();
		this.victoryPoints = victoryPoints;
	}

	public Player getOwner() {
		return owner;
	}

	public Integer getVictoryPoints() {
		return victoryPoints;
	}

	protected abstract void setItemCost();
}
