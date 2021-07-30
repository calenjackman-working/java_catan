package model.board;

import java.util.ArrayList;
import java.util.List;

public class Tile
{
	private Integer row, column;
	private List<Node> nodes;
	private ResourceType resourceType;

	public Tile(Integer row, Integer column)
	{
		this.row = row;
		this.column = column;
		this.nodes = new ArrayList<>();
		this.resourceType = null;
	}

	public Tile(Integer row, Integer column, List<Node> nodes)
	{
		this(row, column);
		this.nodes = nodes;
	}

	public List<Node> getNodes()
	{
		return nodes;
	}

	public Boolean addNode(Node n)
	{
		if (this.nodes.contains(n))
		{
			return false;
		}

		this.nodes.add(n);
		return true;
	}

	public Boolean removeNode(Node n)
	{
		if (!(this.nodes.contains(n)))
		{
			return false;
		}

		this.nodes.remove(n);
		return true;
	}

	public ResourceType getResourceType()
	{
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType)
	{
		this.resourceType = resourceType;
	}

	public Integer getRow()
	{
		return row;
	}

	public Integer getColumn()
	{
		return column;
	}
}
