package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

import exceptions.NoPlayersInGameException;
import exceptions.PlayerDoesNotExistException;
import exceptions.PlayerNameTakenException;
import exceptions.TooManyPlayersException;
import model.Game;

public class GameTest {
    private Game game;

    @Before
    public void setUp() {
        this.game = new Game(null);
    }

    @Test
    public void addPlayerTest() {
        try {
            this.game.addPlayer("Calen");
            assertTrue("There should be one player in the game", this.game.getPlayers().size() == 1);
        } catch (TooManyPlayersException | PlayerNameTakenException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void addAllPlayersTest() {
        try {
            this.game.addAllPlayers(Arrays.asList("Calen", "Aubrey", "Ella", "Lucy"));
            assertTrue("Game should be full", this.game.getPlayers().size() == 4);
        } catch (TooManyPlayersException | PlayerNameTakenException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void addTooManyPlayers() {
        try {
            this.game.addAllPlayers(Arrays.asList("Calen", "Aubrey", "Ella", "Lucy"));
            assertTrue("Game should be full", this.game.getPlayers().size() == 4);
            this.game.addPlayer("Random");
        } catch (TooManyPlayersException | PlayerNameTakenException e) {
            if (e.getClass().equals(TooManyPlayersException.class)) {
                assertTrue("Exception should be thrown", true);
            } else {
                fail("Incorrect exception thrown");
            }
        }
    }

    @Test
    public void addPlayerWithSameName() {
        try {
            this.game.addPlayer("Calen");
            this.game.addPlayer("Calen");
            fail("Exception should have been thrown");
        } catch (TooManyPlayersException | PlayerNameTakenException e) {
            if (e.getClass().equals(PlayerNameTakenException.class)) {
                assertTrue("Exception should be thrown", true);
            } else {
                fail("Incorrect exception thrown");
            }
        }
    }

    @Test
    public void removePlayerTest() {
        try {
            this.game.addAllPlayers(Arrays.asList("Calen", "Aubrey", "Ella", "Lucy"));
            assertTrue("There should only be 4 players in the game", this.game.getPlayers().size() == 4);
            this.game.removePlayer("Calen");
            assertTrue("There should only be 3 players in the game", this.game.getPlayers().size() == 3);
        } catch (PlayerDoesNotExistException | NoPlayersInGameException | TooManyPlayersException
                | PlayerNameTakenException e) {
            fail("Exceptions should not be thrown");
        }
    }

    @Test
    public void removeNonExistentPlayerTest() {
        try {
            this.game.addAllPlayers(Arrays.asList("Calen", "Aubrey", "Ella"));
            this.game.removePlayer("Lucy");
            fail("Exception should have been thrown");
        } catch (PlayerDoesNotExistException | NoPlayersInGameException | TooManyPlayersException
                | PlayerNameTakenException e) {
            if (e.getClass().equals(PlayerDoesNotExistException.class)) {
                assertTrue("Exception should be thrown", true);
            } else {
                fail("Incorrect exception thrown");
            }
        }
    }

    @Test
    public void removePlayerFromEmptyList() {
        try {
            this.game.removePlayer("Lucy");
            fail("Exception should have been thrown");
        } catch (PlayerDoesNotExistException | NoPlayersInGameException e) {
            if (e.getClass().equals(NoPlayersInGameException.class)) {
                assertTrue("Exception should be thrown", true);
            } else {
                fail("Incorrect exception thrown");
            }
        }
    }
}
