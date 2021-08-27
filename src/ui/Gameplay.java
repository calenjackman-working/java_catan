package ui;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import exceptions.NoPlayersInGameException;
import exceptions.PlayerDoesNotExistException;
import exceptions.PlayerNameTakenException;
import exceptions.TooManyPlayersException;
import model.Game;
import model.player.Player;

public class Gameplay {
	private Game game;
	private CatanWindow window;
	private CLInterface clInterface;

	public Gameplay(Game game, CatanWindow window) {
		this.game = game;
		this.window = window;
		this.clInterface = new CLInterface();
	}

	public void start() {
		System.out.println("Welcome to Settlers of Calendonia");
		System.out.println("Would you like to start the setup phase? (y / n)");
		String s = clInterface.getMCAnswer(Arrays.asList("y", "n"));

		if (!(s.equals("y"))) {
			this.window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		}

		System.out.println("Starting game setup");
		setupGame();

		System.out.println("Beginning game");
		play();
	}

	private void setupGame() {
		addPlayers();
		setPlayerTurnOrder();
		placeFirstPieces();
	}

	private void addPlayers() {
		addHumanPlayers();
		if (game.getPlayers().size() < 4) {
			addOtherPlayers();
			// addAIPlayers();
		}
	}

	private void addHumanPlayers() {
		System.out.println("Would you like to add human players to the game? (y / n)");
		String addHumanPlayers = clInterface.getMCAnswer(Arrays.asList("y", "n"));
		if (!(addHumanPlayers.equals("y"))) {
			return;
		}
		Boolean addAnotherPlayer = true;
		Boolean roomForMorePlayers = true;
		while (addAnotherPlayer && roomForMorePlayers) {
			Boolean playerAddExceptionThrown = false;
			String playerName = getPlayerName();
			String playerColor = "";
			List<String> remainingColors = getRemainingColors();
			if (remainingColors.size() <= 1) {
				playerColor = remainingColors.get(0);
				System.out.println(playerColor + " is the only color remaining and will be assigned to this player");
			} else {
				playerColor = getPlayerColor(remainingColors);
			}
			try {
				this.game.addPlayer(playerName);
				this.game.getPlayer(playerName).setPlayerColor(playerColor);
			} catch (PlayerNameTakenException e) {
				System.out.println(
						"Player name has already been taken please try to add this player with a different name");
				playerAddExceptionThrown = true;
				continue;
			} catch (TooManyPlayersException e) {
				System.out.println("The game has exceeded the amount of players that can be in a game");
				playerAddExceptionThrown = true;
				removePlayerOption();
			} catch (PlayerDoesNotExistException e) {
				System.out.println("Error occured with player add for " + playerName
						+ ". Please validate that the \"addPlayer\" function is working correctly.");
				playerAddExceptionThrown = true;
				continue;
			} finally {
				roomForMorePlayers = this.game.getPlayers().size() < 4;
				if (roomForMorePlayers) {
					if (!(playerAddExceptionThrown)) {
						addAnotherPlayer = addAnotherPlayerOption();
					}
				} else {
					System.out.println("Game is full starting the game");
				}
			}
		}
	}

	private String getPlayerName() {
		Scanner scanner = MyScanner.getInstance();
		Integer numberPlayers = this.game.getPlayers().size() + 1;
		String playerName = "Player" + numberPlayers.toString();
		Integer loopCount = 0;
		Boolean validInput = false;
		while (!validInput) {
			if (loopCount == 0) {
				System.out.println();
				System.out.println("Please enter a player name. It must follow the below rules:");
				System.out.println("- length between 4 - 15 characters");
				System.out.println("- no special characters (!, @, #, ?, etc.)");
				System.out
						.println("(If you only hit the enter key, this player's name will be \"" + playerName + "\")");
			}
			String userInput = scanner.nextLine().strip();
			if (!(userInput.length() == 0)) {
				if (!(Pattern.matches("[a-zA-Z0-9]{4,15}", userInput))) {
					System.out.println("Input name does not follow the rules specified above");
					System.out.println("Please submit another name:");
					continue;
				}
				playerName = userInput;
			}
			validInput = true;
		}

		return playerName;
	}

	private String getPlayerColor(List<String> colors) {
		String playerColor = "";
		Integer loopCount = 0;
		Boolean validInput = false;
		HashMap<Integer, String> colorOptions = new HashMap<>();
		List<String> mcOptions = new ArrayList<>();
		while (!validInput) {
			if (loopCount == 0) {
				System.out.println("Please enter the corresponding number for the color you would like:");
				for (String color : colors) {
					Integer colorInt = colors.indexOf(color) + 1;
					colorOptions.put(colorInt, color);
					mcOptions.add(colorInt.toString());
					System.out.println(colorInt.toString() + " - " + color);
				}
				mcOptions.add("0");
				System.out.println("0 - choose color for me");
			}
			String userInput = clInterface.getMCAnswer(mcOptions);

			if (userInput.equals("0")) {
				playerColor = colors.get(0);
				validInput = true;
				continue;
			}

			playerColor = colorOptions.get(Integer.valueOf(userInput));
			System.out.println(
					"Please confirm that you would like your player's color to be " + playerColor + " (y / n)");
			String confirmPlayerColor = clInterface.getMCAnswer(Arrays.asList("y", "n"));

			if (!(confirmPlayerColor.equals("y"))) {
				continue;
			}
			validInput = true;
		}
		return playerColor;
	}

	private List<String> getRemainingColors() {
		List<Player> playerList = this.game.getPlayers();
		HashMap<Color, String> remainingColors = new HashMap<>();
		remainingColors.put(Color.red, "red");
		remainingColors.put(Color.blue, "blue");
		remainingColors.put(Color.orange, "orange");
		remainingColors.put(Color.white, "white");
		for (Player player : playerList) {
			remainingColors.remove(player.getPlayerColor());
		}

		return new ArrayList<String>(remainingColors.values());
	}

	private Boolean removePlayerOption() {
		Boolean result = false;
		System.out.print("Current players:");
		String stringPlayerList = getPlayerListString();
		System.out.println(stringPlayerList);
		System.out.println("Would you like to remove a player to make space for another? (y / n)");
		String removePlayer = clInterface.getMCAnswer(Arrays.asList("y", "n"));
		if (!(removePlayer.equals("y"))) {
			System.out.println("Current player list will stay the same");
			result = false;
		} else {
			result = removePlayer();
		}

		return result;
	}

	private String getPlayerListString() {
		List<String> playerNameList = new ArrayList<>();
		for (Player player : this.game.getPlayers()) {
			playerNameList.add(player.getPlayerName());
		}

		String playerNameListString = playerNameList.toString();

		return playerNameListString.substring(1, playerNameListString.length() - 1);
	}

	private Boolean removePlayer() {
		Boolean result = true;
		Boolean playerRemovalQuit = false;
		List<Player> playerList = this.game.getPlayers();
		HashMap<Integer, Player> playerOptions = new HashMap<>();
		List<String> mcOptions = new ArrayList<>();
		while (!(playerRemovalQuit)) {
			System.out.println("Please enter the number corresponding to the player you would like to remove:");
			for (Player player : playerList) {
				Integer playerInt = playerList.indexOf(player) + 1;
				playerOptions.put(playerInt, player);
				mcOptions.add(playerInt.toString());
				System.out.println(playerInt.toString() + " - " + player.getPlayerName());
			}
			mcOptions.add("0");
			System.out.println("0 - quit player removal");

			String userInput = clInterface.getMCAnswer(mcOptions);

			if (userInput.equals("0")) {
				playerRemovalQuit = true;
				result = false;
				continue;
			}
			Player playerToRemove = playerOptions.get(Integer.valueOf(userInput));
			System.out.println(
					"Please confirm that you would like to remove " + playerToRemove.getPlayerName() + " (y / n)");
			String confirmPlayerRemoval = clInterface.getMCAnswer(Arrays.asList("y", "n"));
			if (!(confirmPlayerRemoval.equals("y"))) {
				playerRemovalQuit = false;
				continue;
			}
			try {
				this.game.removePlayer(playerToRemove.getPlayerName());
			} catch (PlayerDoesNotExistException | NoPlayersInGameException e) {
				System.out.println("Exception : " + e.getClass().toGenericString());
				System.out.println(
						"Error removing player from game. Investigate the addPlayers process in the setup Phase");
				playerRemovalQuit = true;
				result = false;
				continue;
			}
			playerRemovalQuit = true;
			result = true;
		}

		return result;
	}

	private Boolean addAnotherPlayerOption() {
		System.out.println("Would you like to add another player to the game? (y / n)");
		String userInput = clInterface.getMCAnswer(Arrays.asList("y", "n"));
		if (!(userInput.equals("y"))) {
			return false;
		}
		return true;
	}

	private void addAIPlayers() {
		// TODO - addAIPlayers
	}

	private void addOtherPlayers() {
		// TODO - addOtherPlayers
		Integer currentPlayerCount = this.game.getPlayers().size();
		for (int i = currentPlayerCount; i < 4; i++) {
			Integer playerNumber = this.game.getPlayers().size() + 1;
			String playerName = "Player" + playerNumber.toString();
			String playerColor = getRemainingColors().get(0);
			try {
				this.game.addPlayer(playerName);
				this.game.getPlayer(playerName).setPlayerColor(playerColor);
			} catch (Exception e) {
				System.out.println("Error occurred in adding auto-generated players");
				e.printStackTrace();
				break;
			}
		}
	}

	private void setPlayerTurnOrder() {
		List<Integer> turnInts = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
		Collections.shuffle(turnInts);
		List<Player> playerList = this.game.getPlayers();
		for (int i = 0; i < turnInts.size(); i++) {
			playerList.get(i).setTurnNumber(turnInts.get(i));
		}
	}

	private void placeFirstPieces() {
		// TODO - placeFirstPieces
		// 2-round snake draft to place first pieces (two settlements and two roads)
	}

	private void play() {
		// TODO - play
	}

	public Game getGame() {
		return game;
	}

	public CatanWindow getWindow() {
		return window;
	}

	public void setWindow(CatanWindow window) {
		this.window = window;
	}

}
