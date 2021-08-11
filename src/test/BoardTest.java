package test;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import model.board.Board;
import model.board.CatanNode;
import model.board.Tile;

public class BoardTest {
	private Board board;

	@Before
	public void setUp() {
		this.board = new Board();
	}

	@Test
	public void nodeGridCorrectSize() {
		List<CatanNode> nodes = this.board.getNodes();
		assertTrue("Nodes not the correct size", nodes.size() == 54);
	}

	@Test
	public void eachNodeShouldHaveAdjacentNodes() {
		List<CatanNode> nodes = this.board.getNodes();
		for (CatanNode node : nodes) {
			String message = "Node at row: " + node.getRow().toString() + " col: " + node.getRow().toString()
					+ " has no adjacent nodes";
			Boolean condition = node.getAdjacentNodes().size() > 0;
			assertTrue(message, condition);
		}
	}

	@Test
	public void correctTileAmount() {
		String message = "Incorrect amount of tiles created";
		Boolean condition = this.board.getTiles().size() == 19;
		assertTrue(message, condition);
	}

	@Test
	public void eachTileHasCorrectAmountOfNodes() {
		List<Tile> boardTiles = this.board.getTiles();
		for (Tile tile : boardTiles) {
			String message = "Tile at index: " + boardTiles.indexOf(tile) + " has incorrect amount of nodes";
			Boolean condition = tile.getNodes().size() == 6;
			assertTrue(message, condition);
		}
	}

	@Test
	public void eachNodeHasATile() {
		for (CatanNode node : this.board.getNodes()) {
			String message = "Node at row: " + node.getRow().toString() + " col: " + node.getRow().toString()
					+ " has no adjacent nodes";
			Boolean condition = node.getTiles().size() > 0;
			assertTrue(message, condition);
		}
	}

	@Test
	public void eachTileHasResource() {
		for (Tile tile : this.board.getTiles()) {
			String message = "Tile at row: " + tile.getRow().toString() + " col: " + tile.getRow().toString()
					+ " has no resource type";
			Boolean condition = tile.getResourceType() != null;
			assertTrue(message, condition);
		}
	}

	@Test
	public void getNodeforValidIndex() {
		try {
			board.getNode(6);
		} catch (IndexOutOfBoundsException e) {
			fail("Exception should not have been thrown for a valid index");
		}
	}

	@Test
	public void getNodeForInvalidIndex() {
		try {
			board.getNode(100);
			fail("Index out of bounds exceptions should have been thrown");
		} catch (IndexOutOfBoundsException e) {
			assertTrue("Index out of bounds exception caught", true);
		}
	}
}
