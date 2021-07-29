package model.board;

import java.util.ArrayList;
import java.util.List;

public class Tile
{
	private List<Node> nodes;

	public Tile()
	{
		this.nodes = new ArrayList<>();
	}

	public Tile(List<Node> nodes)
	{
		this();
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
}
