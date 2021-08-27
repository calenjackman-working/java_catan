package model.player;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import exceptions.IllegalActionException;
import model.board.Board;
import model.board.pieces.ownable.Road;
import model.board.pieces.ownable.Settlement;
import model.player.playerAction.ActionType;
import model.player.playerAction.PlayerAction;

public class Player {
    private Board board;
    private String playerName;
    private PlayerAction playerAction;
    private Color playerColor;
    private Integer turnNumber;

    public Player(Board b, String name) {
        this.board = b;
        this.playerName = name;
        this.playerAction = null;
        this.playerColor = null;
        this.turnNumber = null;
    }

    public void setPlayerAction(ActionType at) {
        if (this.playerAction != null) {
            playerAction.setActionType(at);
        } else {
            this.playerAction = new PlayerAction(at);
        }
    }

    public void executePlayerAction() throws IllegalActionException {
        playerAction.takeAction(board);
    }

    public Board getBoard() {
        return board;
    }

    public PlayerAction getPlayerAction() {
        return playerAction;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public List<Settlement> getSettlements() {
        return board.getSettlements(this);
    }

    public List<Road> getRoads() {
        return board.getRoads(this);
    }

    public Integer getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(Integer turnNumber) {
        this.turnNumber = turnNumber;
    }

    @Override
    public String toString() {
        String msg = "Name: " + this.playerName;
        return msg;
    }

    public void setPlayerColor(String playerColor) {
        HashMap<String, Color> playerColors = new HashMap<>();
        playerColors.put("red", Color.red);
        playerColors.put("orange", Color.orange);
        playerColors.put("blue", Color.blue);
        playerColors.put("white", Color.white);

        String lowerPlayerColor = playerColor.toLowerCase();
        this.playerColor = playerColors.get(lowerPlayerColor);
    }
}
