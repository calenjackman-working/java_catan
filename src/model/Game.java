package model;

import java.util.ArrayList;
import java.util.List;

import exceptions.NoPlayersInGameException;
import exceptions.PlayerDoesNotExistException;
import exceptions.PlayerNameTakenException;
import exceptions.TooManyPlayersException;
import model.board.Board;
import model.enums.GamePhase;
import model.player.Player;
import ui.CatanWindow;
import ui.Gameplay;

public class Game {
    private Board board;
    private List<Player> players;
    private GamePhase gamePhase;
    private Integer turnNumber;
    private Gameplay gameplay;

    public Game(List<Player> players) {
        this.board = new Board();
        this.players = players == null ? new ArrayList<>() : players;
        this.gamePhase = GamePhase.SETUP;
        this.turnNumber = 0;
    }

    public void play(CatanWindow catanWindow) {
        this.gameplay = new Gameplay(this, catanWindow);
        gameplay.start();
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public Integer getTurnNumber() {
        return turnNumber;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public Player getPlayer(String name) throws PlayerDoesNotExistException {
        for (Player player : players) {
            if (player.getPlayerName().equals(name)) {
                return player;
            }
        }
        throw new PlayerDoesNotExistException("No player with Name = " + name + " found in this game.");
    }

    public Player getPlayer(Integer turnNumber) throws PlayerDoesNotExistException {
        for (Player player : players) {
            if (player.getTurnNumber().equals(turnNumber)) {
                return player;
            }
        }
        throw new PlayerDoesNotExistException("No player with TurnNumber = " + turnNumber + " found in this game.");
    }

    public void addPlayer(String name) throws TooManyPlayersException, PlayerNameTakenException {
        if (gameIsFull()) {
            throw new TooManyPlayersException(
                    "There are already 4 players in this game. Please remove a player if you would like to add another.");
        } else if (playerNameTaken(name)) {
            throw new PlayerNameTakenException(
                    name + " is already being used as a name for another player. Please select another name.");
        } else {
            this.players.add(new Player(this.board, name));
        }
    }

    public void addAllPlayers(List<String> playerNames) throws TooManyPlayersException, PlayerNameTakenException {
        try {
            for (String name : playerNames) {
                addPlayer(name);
            }
        } catch (TooManyPlayersException | PlayerNameTakenException e) {
            throw e;
        }
    }

    public void removePlayer(String name) throws PlayerDoesNotExistException, NoPlayersInGameException {
        if (this.players.size() != 0) {
            for (Player player : this.players) {
                if (player.getPlayerName().equals(name)) {
                    this.players.remove(player);
                    return;
                }
            }
            throw new PlayerDoesNotExistException("No player with Name = " + name + " found in this game.");
        } else {
            throw new NoPlayersInGameException("There are no players in this game yet.");
        }
    }

    public void removeAllPlayers(List<String> playerNames)
            throws PlayerDoesNotExistException, NoPlayersInGameException {
        for (String name : playerNames) {
            removePlayer(name);
        }
    }

    private boolean gameIsFull() {
        return this.players.size() == 4;
    }

    private Boolean playerNameTaken(String name) {
        for (Player player : players) {
            if (player.getPlayerName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
