package model.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import model.board.ownable.Road;
import model.board.ownable.Settlement;
import model.enums.ResourceType;
import model.player.Player;

public class Board {
	private List<CatanNode> nodes;
	private List<Tile> tiles;

	public Board() {
		this.nodes = new ArrayList<>();
		this.tiles = new ArrayList<>();

		populateNodes();
		populateTiles();
	}

	private void populateNodes() {
		initializeNodes();
		assignNodeChildren();
	}

	private void initializeNodes() {
		Integer currentRow = 0;
		Integer startCol = 3;
		Integer nodesInRow = 3;
		Integer innerRowCounter = 0;

		Boolean afterMiddleRow = false;
		Boolean emptyRow = false;

		do {
			Integer colCursor = startCol;
			if (!(emptyRow)) {
				for (int i = 0; i < nodesInRow; i++) {
					this.nodes.add(new CatanNode(currentRow, colCursor));
					colCursor += 2;
				}

				innerRowCounter++;
			} else {
				emptyRow = false;
				innerRowCounter = 0;
			}

			currentRow++;

			if ((startCol - 1 < 0) && (nodesInRow + 1 > 6)) {
				afterMiddleRow = true;
			}

			if (innerRowCounter == 1) {
				if (afterMiddleRow) {
					startCol++;
					nodesInRow--;
				} else {
					startCol--;
					nodesInRow++;
				}
			} else if (innerRowCounter == 2) {
				emptyRow = true;
			}
		} while (currentRow < 17);
	}

	private void assignNodeChildren() {
		for (CatanNode node : this.nodes) {
			Integer nodeRow = node.getRow();
			Integer nodeCol = node.getColumn();

			CatanNode above = getNode(nodeRow - 2, nodeCol);
			CatanNode below = getNode(nodeRow + 2, nodeCol);
			CatanNode topLeft = getNode(nodeRow - 1, nodeCol - 1);
			CatanNode topRight = getNode(nodeRow - 1, nodeCol + 1);
			CatanNode botLeft = getNode(nodeRow + 1, nodeCol - 1);
			CatanNode botRight = getNode(nodeRow + 1, nodeCol + 1);

			List<CatanNode> possibleAdjacentNodes = new ArrayList<>();
			possibleAdjacentNodes.addAll(Arrays.asList(above, below, topLeft, topRight, botLeft, botRight));

			for (CatanNode pNode : possibleAdjacentNodes) {
				if (!(pNode == null)) {
					node.addAdjacentNode(pNode);
				}
			}
		}
	}

	private void populateTiles() {
		initializeTiles();
		assignResources();
		assignRollNumber();
	}

	private void initializeTiles() {
		Integer startRow = 0;
		Integer startCol = 3;
		Integer tilesInRow = 3;
		Integer tileRow = 0;
		Integer tileStartCol = 2;

		Boolean afterMiddleRow = false;

		do {
			Integer colCursor = startCol;
			Integer tileColCursor = tileStartCol;
			for (int i = 0; i < tilesInRow; i++) {
				Tile currTile = new Tile(tileRow, tileColCursor);

				currTile.addNode(this.getNode(startRow + 0, colCursor + 0));
				currTile.addNode(this.getNode(startRow + 1, colCursor - 1));
				currTile.addNode(this.getNode(startRow + 3, colCursor - 1));
				currTile.addNode(this.getNode(startRow + 4, colCursor + 0));
				currTile.addNode(this.getNode(startRow + 3, colCursor + 1));
				currTile.addNode(this.getNode(startRow + 1, colCursor + 1));

				for (CatanNode node : currTile.getNodes()) {
					node.addTile(currTile);
				}

				this.tiles.add(currTile);

				colCursor += 2;
				tileColCursor += 2;
			}

			startRow += 3;
			tileRow++;

			if ((startCol - 1 < 1) && (tilesInRow + 1 > 5)) {
				afterMiddleRow = true;
			}

			if (afterMiddleRow) {
				startCol += 1;
				tileStartCol += 1;
				tilesInRow -= 1;
			} else {
				startCol -= 1;
				tileStartCol -= 1;
				tilesInRow += 1;
			}
		} while (startRow <= 12);
	}

	private void assignResources() {
		HashMap<ResourceType, Integer> resourceAmounts = initResourceAmounts();
		List<Integer> randomTileOrder = new ArrayList<>();
		for (int i = 0; i < 19; i++) {
			randomTileOrder.add(i);
		}

		Collections.shuffle(randomTileOrder);
		Set<ResourceType> resourceKeys = resourceAmounts.keySet();

		for (int i = 0; i < randomTileOrder.size(); i++) {
			for (ResourceType rType : resourceKeys) {
				if (resourceAmounts.get(rType) > 0) {
					this.tiles.get(randomTileOrder.get(i)).setResourceType(rType);
					resourceAmounts.put(rType, resourceAmounts.get(rType) - 1);
					break;
				}
			}
		}
	}

	private HashMap<ResourceType, Integer> initResourceAmounts() {
		HashMap<ResourceType, Integer> temp = new HashMap<>();
		temp.put(ResourceType.LUMBER, 4);
		temp.put(ResourceType.GRAIN, 4);
		temp.put(ResourceType.WOOL, 4);
		temp.put(ResourceType.BRICK, 3);
		temp.put(ResourceType.ORE, 3);
		temp.put(ResourceType.NOTHING, 1);

		return temp;
	}

	private void assignRollNumber() {
		return;
	}

	public List<CatanNode> getNodes() {
		return nodes;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public List<Settlement> getSettlements() {
		List<Settlement> listOfSettlements = new ArrayList<>();
		for (CatanNode node : this.nodes) {
			Settlement nodeSettlement = node.getSettlement();
			if (!(nodeSettlement == null)) {
				listOfSettlements.add(nodeSettlement);
			}
		}
		return listOfSettlements;
	}

	public List<Settlement> getSettlements(Player player) {
		List<Settlement> listOfSettlements = new ArrayList<>();
		for (CatanNode node : this.nodes) {
			Settlement nodeSettlement = node.getSettlement();
			if (!(nodeSettlement == null) && (nodeSettlement.getOwner().equals(player))) {
				listOfSettlements.add(nodeSettlement);
			}
		}
		return listOfSettlements;
	}

	public List<Road> getRoads() {
		List<Road> listOfRoads = new ArrayList<>();
		for (CatanNode node : this.nodes) {
			List<Road> nodeRoads = node.getRoads();
			for (Road road : nodeRoads) {
				if (!(road == null) && !(listOfRoads.contains(road))) {
					listOfRoads.add(road);
				}
			}
		}
		return listOfRoads;
	}

	public List<Road> getRoads(Player player) {
		List<Road> listOfRoads = new ArrayList<>();
		for (CatanNode node : this.nodes) {
			List<Road> nodeRoads = node.getRoads();
			for (Road road : nodeRoads) {
				if (!(road == null) && !(listOfRoads.contains(road)) && (road.getOwner().equals(player))) {
					listOfRoads.add(road);
				}
			}
		}
		return listOfRoads;
	}

	public CatanNode getNode(Integer row, Integer column) {
		for (CatanNode node : this.nodes) {
			if ((node.getRow() == row) && (node.getColumn() == column)) {
				return node;
			}
		}
		return null;
	}

	public CatanNode getNode(Integer index) throws IndexOutOfBoundsException {
		try {
			return this.nodes.get(index);
		} catch (IndexOutOfBoundsException exception) {
			throw exception;
		}
	}
}
