package model.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Board
{
	private List<Node> nodes;
	private List<Tile> tiles;

	public Board()
	{
		this.nodes = new ArrayList<>();
		this.tiles = new ArrayList<>();

		populateNodes();
		populateTiles();
	}

	private void populateNodes()
	{
		initializeNodes();
		assignNodeChildren();
	}

	private void initializeNodes()
	{
		Integer currentRow = 0;
		Integer startCol = 3;
		Integer nodesInRow = 3;
		Integer innerRowCounter = 0;

		Boolean afterMiddleRow = false;
		Boolean emptyRow = false;

		do
		{
			Integer colCursor = startCol;
			if (!(emptyRow))
			{
				for (int i = 0; i < nodesInRow; i++)
				{
					this.nodes.add(new Node(currentRow, colCursor));
					colCursor += 2;
				}

				innerRowCounter++;
			} else
			{
				emptyRow = false;
				innerRowCounter = 0;
			}

			currentRow++;

			if ((startCol - 1 < 0) && (nodesInRow + 1 > 6))
			{
				afterMiddleRow = true;
			}

			if (innerRowCounter == 1)
			{
				if (afterMiddleRow)
				{
					startCol++;
					nodesInRow--;
				} else
				{
					startCol--;
					nodesInRow++;
				}
			} else if (innerRowCounter == 2)
			{
				emptyRow = true;
			}
		} while (currentRow < 17);
	}

	private void assignNodeChildren()
	{
		for (Node node : this.nodes)
		{
			Integer nodeRow = node.getRow();
			Integer nodeCol = node.getColumn();

			Node above = getNode(nodeRow - 2, nodeCol);
			Node below = getNode(nodeRow + 2, nodeCol);
			Node topLeft = getNode(nodeRow - 1, nodeCol - 1);
			Node topRight = getNode(nodeRow - 1, nodeCol + 1);
			Node botLeft = getNode(nodeRow + 1, nodeCol - 1);
			Node botRight = getNode(nodeRow + 1, nodeCol + 1);

			List<Node> possibleAdjacentNodes = new ArrayList<>();
			possibleAdjacentNodes
					.addAll(Arrays.asList(above, below, topLeft, topRight, botLeft, botRight));

			for (Node pNode : possibleAdjacentNodes)
			{
				if (!(pNode == null))
				{
					node.addAdjacentNode(pNode);
				}
			}
		}
	}

	private void populateTiles()
	{
		initializeTiles();
		assignTilesToResource();
	}

	private void initializeTiles()
	{
		Integer startRow = 0;
		Integer startCol = 3;
		Integer tilesInRow = 3;
		Integer tileRow = 0;
		Integer tileStartCol = 2;

		Boolean afterMiddleRow = false;

		do
		{
			Integer colCursor = startCol;
			Integer tileColCursor = tileStartCol;
			for (int i = 0; i < tilesInRow; i++)
			{
				Tile currTile = new Tile(tileRow, tileColCursor);

				currTile.addNode(this.getNode(startRow + 0, colCursor + 0));
				currTile.addNode(this.getNode(startRow + 1, colCursor - 1));
				currTile.addNode(this.getNode(startRow + 3, colCursor - 1));
				currTile.addNode(this.getNode(startRow + 4, colCursor + 0));
				currTile.addNode(this.getNode(startRow + 3, colCursor + 1));
				currTile.addNode(this.getNode(startRow + 1, colCursor + 1));

				for (Node node : currTile.getNodes())
				{
					node.addTile(currTile);
				}

				this.tiles.add(currTile);

				colCursor += 2;
				tileColCursor += 2;
			}

			startRow += 3;
			tileRow++;

			if ((startCol - 1 < 1) && (tilesInRow + 1 > 5))
			{
				afterMiddleRow = true;
			}

			if (afterMiddleRow)
			{
				startCol += 1;
				tileStartCol += 1;
				tilesInRow -= 1;
			} else
			{
				startCol -= 1;
				tileStartCol -= 1;
				tilesInRow += 1;
			}
		} while (startRow <= 12);
	}

	private void assignTilesToResource()
	{
		HashMap<ResourceType, Integer> resourceAmounts = initResourceAmounts();
		List<Integer> randomTileOrder = new ArrayList<>();
		for (int i = 0; i < 19; i++)
		{
			randomTileOrder.add(i);
		}

		Collections.shuffle(randomTileOrder);
		Set<ResourceType> resourceKeys = resourceAmounts.keySet();

		for (int i = 0; i < randomTileOrder.size(); i++)
		{
			for (ResourceType rType : resourceKeys)
			{
				if (resourceAmounts.get(rType) > 0)
				{
					this.tiles.get(randomTileOrder.get(i)).setResourceType(rType);
					resourceAmounts.put(rType, resourceAmounts.get(rType) - 1);
					break;
				}
			}
		}
	}

	public HashMap<ResourceType, Integer> initResourceAmounts()
	{
		HashMap<ResourceType, Integer> temp = new HashMap<>();
		temp.put(ResourceType.LUMBER, 4);
		temp.put(ResourceType.GRAIN, 4);
		temp.put(ResourceType.WOOL, 4);
		temp.put(ResourceType.BRICK, 3);
		temp.put(ResourceType.ORE, 3);
		temp.put(ResourceType.NOTHING, 1);

		return temp;
	}

	public List<Node> getNodes()
	{
		return nodes;
	}

	public Node getNode(Integer row, Integer column)
	{
		for (Node node : this.nodes)
		{
			if ((node.getRow() == row) && (node.getColumn() == column))
			{
				return node;
			}
		}
		return null;
	}

	public Node getNode(Integer index)
	{
		try
		{
			return this.nodes.get(index);
		} catch (IndexOutOfBoundsException exception)
		{
			return null;
		}
	}

	public List<Tile> getTiles()
	{
		return tiles;
	}
}
