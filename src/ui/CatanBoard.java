package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import model.Game;
import model.board.CatanNode;
import model.board.Tile;
import model.enums.ResourceType;
import model.player.Player;

public class CatanBoard extends JPanel {
    private Game game;
    private Thread thread;
    private Animate animate;
    private CatanWindow catanWindow;

    // private CatanEventListener catanEventListener;

    public CatanBoard(CatanWindow window) {
        this(window, new Game(null), null);
    }

    public CatanBoard(CatanWindow window, Game game, List<Player> playerList) {
        // leftover from when this class extended JButton
        // super.setBorderPainted(false);
        // super.setFocusPainted(false);
        // super.setContentAreaFilled(false);
        super.setBackground(Color.LIGHT_GRAY);
        // this.addActionListener(new ClickListener());

        this.catanWindow = window;
        this.game = (game == null) ? new Game(playerList) : game;

        // TODO - implement GUI
        // this.catanEventListener = new CatanEventListener(this);
        // addKeyListener(this.catanEventListener);

        // the below lines can be deleted once full GUI is implemented for mouse
        // clicking interaction with Catan board
        this.setAnimate(new Animate(this));
        this.setThread(new Thread(this.getAnimate()));
        this.getThread().start();

        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setRenderingHints(rh);

        drawCatanBoard(g2D);
    }

    private void drawCatanBoard(Graphics2D g2D) {
        Point2D.Double startCoord = new Point2D.Double((double) 10, (double) 10);
        Double sideLength = (double) 100;

        for (Tile tile : this.game.getBoard().getTiles()) {
            drawTile(g2D, startCoord, tile, sideLength);
        }
    }

    private void drawTile(Graphics2D g2D, java.awt.geom.Point2D.Double startCoord, Tile tile, Double sideLength) {
        HashMap<ResourceType, Color> colorMap = initColorMap();

        Double yUnits = sideLength / 2;
        Double xUnits = yUnits * Math.sqrt(3);
        Color tileColor = colorMap.get(tile.getResourceType());

        Path2D.Double path = new Path2D.Double();

        for (CatanNode node : tile.getNodes()) {
            Double yDev = node.getRow() * yUnits;
            Double xDev = node.getColumn() * xUnits;

            if (tile.getNodes().indexOf(node) == 0) {
                path.moveTo(startCoord.getX() + xDev, startCoord.getY() + yDev);
            } else {
                path.lineTo(startCoord.getX() + xDev, startCoord.getY() + yDev);
            }
        }

        CatanNode topNode = tile.getNodes().get(0);

        // coord for resource label
        Double centerXDev = startCoord.getX() + (topNode.getColumn() * xUnits);
        Double centerYDev = startCoord.getX() + ((topNode.getRow() + 2) * yUnits);
        Point2D.Double centerOfTile = new Point2D.Double(centerXDev, centerYDev);

        // coord for tileID label
        Double topYDev = startCoord.getX() + ((topNode.getRow() + 0.5) * yUnits);
        Point2D.Double topMiddleOfTile = new Point2D.Double(centerXDev, topYDev);

        String tileRollNumberLabel = "[" + tile.getRollNumber().toString() + "]";
        String tileIDLabel = "(" + tile.getId().toString() + ")";

        path.closePath();
        g2D.setColor(tileColor);
        g2D.fill(path);
        g2D.setColor(Color.black);
        g2D.draw(path);

        // g2D.setFont(new Font(g2D.getFont().getFontName(), Font.BOLD, 20));
        g2D.setFont(new Font("Consolas", Font.BOLD, 20));
        FontMetrics fMetrics = g2D.getFontMetrics(g2D.getFont());

        Integer sW = fMetrics.stringWidth(tileRollNumberLabel);
        Integer sH = fMetrics.getHeight();
        Integer xlabelAdjust = sW / 2;
        Integer yLabelAdjust = sH / 2;

        g2D.drawString(tileRollNumberLabel, (float) centerOfTile.getX() - xlabelAdjust,
                (float) centerOfTile.getY() + yLabelAdjust);

        // g2D.setFont(new Font(g2D.getFont().getFontName(), Font.PLAIN, 12));
        g2D.setFont(new Font("Consolas", Font.ITALIC, 12));
        fMetrics = g2D.getFontMetrics(g2D.getFont());
        // g2D.setColor(Color.lightGray);

        sW = fMetrics.stringWidth(tileIDLabel);
        sH = fMetrics.getHeight();
        xlabelAdjust = sW / 2;
        yLabelAdjust = sH / 2;

        g2D.drawString(tileIDLabel, (float) centerOfTile.getX() - xlabelAdjust,
                (float) topMiddleOfTile.getY() + yLabelAdjust);
    }

    private HashMap<ResourceType, Color> initColorMap() {
        HashMap<ResourceType, Color> temp = new HashMap<>();

        temp.put(ResourceType.BRICK, Color.decode("#8c4b25"));
        temp.put(ResourceType.LUMBER, Color.decode("#38582f"));
        temp.put(ResourceType.ORE, Color.decode("#64636c"));
        temp.put(ResourceType.GRAIN, Color.decode("#f5c547"));
        temp.put(ResourceType.WOOL, Color.decode("#9f8c69"));
        temp.put(ResourceType.NOTHING, Color.decode("#e2be7b"));

        return temp;
    }

    public Thread getThread() {
        return thread;
    }

    public Animate getAnimate() {
        return animate;
    }

    public CatanWindow getCatanWindow() {
        return catanWindow;
    }

    public Game getGame() {
        return game;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public void setAnimate(Animate animate) {
        this.animate = animate;
    }

    public void update() {
        this.repaint();
    }
}
