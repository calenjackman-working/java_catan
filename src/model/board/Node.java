package model.board;

import java.util.ArrayList;
import java.util.List;

public class Node
{
	private Integer row, column;
	private List<Node> adjacentNodes;
	private List<Tile> tiles;
	private Boolean settlement;

	public Node(Integer row, Integer column)
	{
		this.row = row;
		this.column = column;
		this.adjacentNodes = new ArrayList<>();
		this.settlement = false;
	}

	public Integer getRow()
	{
		return row;
	}

	public Integer getColumn()
	{
		return column;
	}

	public Boolean getSettlement()
	{
		return settlement;
	}

	public List<Tile> getTiles()
	{
		return tiles;
	}

	public List<Node> getAdjacentNodes()
	{
		return adjacentNodes;
	}

	public Boolean addAdjacentNode(Node n)
	{
		if (this.adjacentNodes.contains(n))
		{
			return false;
		}

		this.adjacentNodes.add(n);

		return true;

	}

	public Boolean removeAdjacentNode(Node n)
	{
		if (!(this.adjacentNodes.contains(n)))
		{
			return false;
		}

		this.adjacentNodes.remove(n);
		return true;
	}
}
