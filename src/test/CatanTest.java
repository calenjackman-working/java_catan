package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import ui.CatanWindow;

public class CatanTest {
	private CatanWindow catanWindow;
	private ByteArrayOutputStream os;

	@Before
	public void setUp() {
		try {
			this.catanWindow = new CatanWindow();
			this.catanWindow.setDefaultCloseOperation(CatanWindow.EXIT_ON_CLOSE);
			this.catanWindow.setVisible(false);
			this.os = new ByteArrayOutputStream();
			System.setOut(new PrintStream(os, false, "UTF-8"));
		} catch (IOException e) {
			fail(e.getClass().toGenericString());
		}
	}

	@Test
	public void gameplayTest() {
		String userInput = "";
		System.setIn(new ByteArrayInputStream(userInput.getBytes()));
		catanWindow.getCatanBoard().getGame().play(catanWindow);
		try {
			String foobarOutput = os.toString().strip();
			assertTrue(foobarOutput.equals("Calen Jackman"));
		} catch (Exception e) {
			fail(e.getClass().toGenericString());
		}
	}
}
