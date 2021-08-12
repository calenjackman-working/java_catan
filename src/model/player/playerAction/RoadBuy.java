package model.player.playerAction;

import java.util.ArrayList;
import java.util.List;

import exceptions.IllegalActionException;
import exceptions.IllegalRoadAdditionException;
import model.board.Board;
import model.board.CatanNode;
import model.board.pieces.ownable.Road;
import model.enums.GamePhase;

public class RoadBuy implements ActionType {
	private Road road;
	private GamePhase gamePhase;

	public RoadBuy(Road road, GamePhase gamePhase) {
		this.road = road;
		this.gamePhase = gamePhase;
	}

	@Override
	public void takeAction(Board board) throws IllegalActionException {
		if (validAction(board)) {
			List<CatanNode> roadNodes = this.road.getNodes();
			for (CatanNode node : roadNodes) {
				node.addRoad(this.road);
			}
		}
	}

	@Override
	public Boolean validAction(Board board) throws IllegalActionException {
		List<ValidityCheckRunner> validityChecks = new ArrayList<>();
		validityChecks.add(new ValidityCheckRunner() {
			@Override
			public Boolean runCheck() throws IllegalRoadAdditionException {
				return nodesAreAdjacent();
			}
		});
		validityChecks.add(new ValidityCheckRunner() {
			@Override
			public Boolean runCheck() throws IllegalRoadAdditionException {
				return pathIsVacant();
			}
		});

		if (this.gamePhase.equals(GamePhase.INPROGRESS)) {
			validityChecks.add(new ValidityCheckRunner() {
				@Override
				public Boolean runCheck() throws IllegalRoadAdditionException {
					return pathIsConnectedToAnotherRoadOwnedByPlayer();
				}
			});
		}

		for (ValidityCheckRunner validityCheck : validityChecks) {
			if (!(validityCheck.runCheck())) {
				return false;
			}
		}
		return true;
	}

	private Boolean nodesAreAdjacent() throws IllegalRoadAdditionException {
		List<CatanNode> roadNodes = this.road.getNodes();
		if (!(roadNodes.get(0).getAdjacentNodes().contains(roadNodes.get(1)))) {
			throw new IllegalRoadAdditionException("Nodes given are not adjacent to each other");
		}
		return true;
	}

	private Boolean pathIsVacant() throws IllegalRoadAdditionException {
		List<CatanNode> roadNodes = this.road.getNodes();
		CatanNode startNode = roadNodes.get(0);
		CatanNode endNode = roadNodes.get(1);
		List<Road> startNodeRoads = startNode.getRoads();
		for (Road road : startNodeRoads) {
			if (road.getNodes().contains(endNode)) {
				throw new IllegalRoadAdditionException("The path selected already contains an existing road");
			}
		}
		return true;
	}

	private Boolean pathIsConnectedToAnotherRoadOwnedByPlayer() throws IllegalRoadAdditionException {
		CatanNode startNode = this.road.getNodes().get(0);
		CatanNode endNode = this.road.getNodes().get(1);

		List<CatanNode> startAdjacentNodes = new ArrayList<>();
		startAdjacentNodes.addAll(startNode.getAdjacentNodes());

		List<CatanNode> endAdjacentNodes = new ArrayList<>();
		endAdjacentNodes.addAll(endNode.getAdjacentNodes());

		for (CatanNode adjacentNode : startAdjacentNodes) {
			List<Road> adjacentNodeRoads = adjacentNode.getRoads();
			for (Road road : adjacentNodeRoads) {
				if ((road.getNodes().contains(startNode)) && (road.getOwner().equals(this.road.getOwner()))) {
					return true;
				}
			}
		}
		throw new IllegalRoadAdditionException(
				"Proposed road is not connected to a road owned by " + this.road.getOwner().getPlayerName());
	}
}
