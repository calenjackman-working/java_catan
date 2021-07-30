package ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CatanWindow extends JFrame
{
	public static final Integer DEFAULT_WIDTH = 1000;
	public static final Integer DEFAULT_HEIGHT = 1000;
	public static final String DEFAULT_TITLE = "Java Catan";

	private CatanBoard catanBoard;

	public CatanWindow()
	{
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE);
	}

	public CatanWindow(Integer width, Integer height, String title)
	{
		super(title);
		super.setSize(width, height);
		super.setLocationByPlatform(true);

		JPanel layout = new JPanel(new BorderLayout());
		this.catanBoard = new CatanBoard(this);

		layout.add(catanBoard);

		this.add(layout);
	}

	public CatanBoard getCatanBoard()
	{
		return catanBoard;
	}
}
