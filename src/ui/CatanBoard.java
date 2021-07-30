package ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import model.Moderator;
import model.board.Node;
import model.board.ResourceType;
import model.board.Tile;
import model.player.Player;

public class CatanBoard extends JButton
{
	private CatanWindow catanWindow;
	private Moderator moderator;
	private List<Player> players;

	public CatanBoard(CatanWindow window)
	{
		this(window, new Moderator(null), null);
	}

	public CatanBoard(CatanWindow window, Moderator mod, List<Player> playerList)
	{
		super.setBorderPainted(false);
		super.setFocusPainted(false);
		super.setContentAreaFilled(false);
		super.setBackground(Color.LIGHT_GRAY);
		// this.addActionListener(new ClickListener());

		this.moderator = (mod == null) ? new Moderator(playerList) : mod;
		this.catanWindow = window;
		this.players = playerList;
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2D.setRenderingHints(rh);

		drawCatanBoard(g2D);
	}

	private void drawCatanBoard(Graphics2D g2D)
	{
		Point2D.Double startCoord = new Point2D.Double((double) 10, (double) 10);
		Double sideLength = (double) 100;

		for (Tile tile : this.moderator.getBoard().getTiles())
		{
			drawTile(g2D, startCoord, tile, sideLength);
		}
	}

	private void drawTile(Graphics2D g2D, java.awt.geom.Point2D.Double startCoord, Tile tile,
			Double sideLength)
	{
		HashMap<ResourceType, Color> colorMap = initColorMap();

		Double yUnits = sideLength / 2;
		Double xUnits = yUnits * Math.sqrt(3);
		Color tileColor = colorMap.get(tile.getResourceType());

		Path2D.Double path = new Path2D.Double();

		for (Node node : tile.getNodes())
		{
			Double yDev = node.getRow() * yUnits;
			Double xDev = node.getColumn() * xUnits;

			if (tile.getNodes().indexOf(node) == 0)
			{
				path.moveTo(startCoord.getX() + xDev, startCoord.getY() + yDev);
			} else
			{
				path.lineTo(startCoord.getX() + xDev, startCoord.getY() + yDev);
			}
		}

		Node topNode = tile.getNodes().get(0);
		Double centerXDev = startCoord.getX() + (topNode.getColumn() * xUnits);
		Double centerYDev = startCoord.getX() + ((topNode.getRow() + 2) * yUnits);
		Point2D.Double centerOfTile = new Point2D.Double(centerXDev, centerYDev);
		String tileResource = tile.getResourceType().name();

		path.closePath();
		g2D.setColor(tileColor);
		g2D.fill(path);
		g2D.setColor(Color.black);
		g2D.draw(path);

		FontMetrics fMetrics = g2D.getFontMetrics(g2D.getFont());

		Integer sW = fMetrics.stringWidth(tileResource);
		Integer sH = fMetrics.getHeight();
		Integer xlabelAdjust = sW / 2;
		Integer yLabelAdjust = sH / 2;

		g2D.drawString(tileResource, (float) centerOfTile.getX() - xlabelAdjust,
				(float) centerOfTile.getY() + yLabelAdjust);
	}

	private HashMap<ResourceType, Color> initColorMap()
	{
		HashMap<ResourceType, Color> temp = new HashMap<>();

		temp.put(ResourceType.BRICK, Color.decode("#8c4b25"));
		temp.put(ResourceType.LUMBER, Color.decode("#38582f"));
		temp.put(ResourceType.ORE, Color.decode("#64636c"));
		temp.put(ResourceType.GRAIN, Color.decode("#f5c547"));
		temp.put(ResourceType.WOOL, Color.decode("#9f8c69"));
		temp.put(ResourceType.NOTHING, Color.decode("#e2be7b"));

		return temp;
	}
}
