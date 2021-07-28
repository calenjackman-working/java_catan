package model.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private List<Tile> populateTiles()
	{
		return null;
	}

	private void populateNodes()
	{
		initializeNodes();
		assignNodeChildren();
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
}
