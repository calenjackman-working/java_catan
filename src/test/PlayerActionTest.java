package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

import exceptions.IllegalActionException;
import exceptions.PlayerDoesNotExistException;
import model.Game;
import model.board.Board;
import model.board.CatanNode;
import model.board.pieces.ownable.Road;
import model.board.pieces.ownable.Settlement;
import model.enums.GamePhase;
import model.player.Player;
import model.player.playerAction.RoadBuy;
import model.player.playerAction.SettlementBuy;

public class PlayerActionTest {
	private Game game;
	private Board board;

	@Before
	public void setUp() {
		this.game = new Game(null);
		this.board = this.game.getBoard();
		try {
			this.game.addAllPlayers(Arrays.asList("Calen"));
			Player player = this.game.getPlayer("Calen");
			Road road = new Road(player, this.board.getNode(0, 3), this.board.getNode(1, 2));
			player.setPlayerAction(new RoadBuy(road, this.game.getGamePhase()));
			player.executePlayerAction();
			Settlement settlement = new Settlement(player, this.board.getNode(0, 3));
			player.setPlayerAction(new SettlementBuy(settlement));
			player.executePlayerAction();
			road = new Road(player, this.board.getNode(3, 6), this.board.getNode(4, 7));
			player.setPlayerAction(new RoadBuy(road, this.game.getGamePhase()));
			player.executePlayerAction();
			settlement = new Settlement(player, this.board.getNode(3, 6));
			player.setPlayerAction(new SettlementBuy(settlement));
			player.executePlayerAction();
			this.game.setGamePhase(GamePhase.INPROGRESS);
		} catch (Exception e) {
			fail(e.getClass().toGenericString());
		}
	}

	@Test
	public void addSettlementTest() {
		try {
			Player player = this.game.getPlayer("Calen");
			Road road = new Road(player, this.board.getNode(4, 7), this.board.getNode(6, 7));
			player.setPlayerAction(new RoadBuy(road, this.game.getGamePhase()));
			player.executePlayerAction();
			CatanNode node = this.game.getBoard().getNode(6, 7);
			Settlement settlement = new Settlement(player, node);
			player.setPlayerAction(new SettlementBuy(settlement));
			player.executePlayerAction();
			assertTrue("\"myNode\" should be identical to the node reference in the new Settlement",
					node.equals(settlement.getNode()));
			assertTrue("\"myPlayer\" should be identical to the owner reference in the new Settlement",
					player.equals(settlement.getOwner()));
			assertTrue("There should be three settlements on the board",
					this.game.getBoard().getSettlements().size() == 3);
		} catch (PlayerDoesNotExistException | IllegalActionException e) {
			fail(e.getClass().toGenericString());
		}
	}

	@Test
	public void addRoadTest() {
		try {
			Player myPlayer = this.game.getPlayer("Calen");
			CatanNode startNode = this.game.getBoard().getNode(1, 2);
			CatanNode endNode = this.game.getBoard().getNode(3, 2);
			Road road = new Road(myPlayer, startNode, endNode);
			myPlayer.setPlayerAction(new RoadBuy(road, this.game.getGamePhase()));
			myPlayer.executePlayerAction();
			assertTrue("\"nodes\" variable of new road should have size 2",
					startNode.getRoads().get(0).getNodes().size() == 2);
		} catch (PlayerDoesNotExistException | IllegalActionException e) {
			fail(e.getClass().toGenericString());
		}
	}
}
