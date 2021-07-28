package test;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import model.board.Board;
import model.board.Node;

public class BoardTest
{
	private Board board;

	@Before
	public void setUp()
	{
		this.board = new Board();
	}

	@Test
	public void nodeGridCorrectSize()
	{
		List<Node> nodes = this.board.getNodes();
		assertTrue("Nodes not the correct size", nodes.size() == 54);
	}

	@Test
	public void eachNodeShouldHaveAdjacentNodes()
	{
		List<Node> nodes = this.board.getNodes();
		for (Node node : nodes)
		{
			String message = "Node at row: " + node.getRow().toString() + " col: "
					+ node.getRow().toString() + " has no adjacent nodes...";
			Boolean condition = node.getAdjacentNodes().size() > 0;
			assertTrue(message, condition);
		}
	}
}
