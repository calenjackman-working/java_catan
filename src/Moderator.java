import java.util.ArrayList;
import java.util.List;

public class Moderator
{
	private Board board;
	private List<Player> players;
	private Integer turnNumber;

	public Moderator()
	{
		this.board = new Board();
		this.players = new ArrayList<>();
		this.turnNumber = 0;
	}
}