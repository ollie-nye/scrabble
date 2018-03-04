package player;

public class PlayersContainer {
	private Player[] players;
	
	private static PlayersContainer instance = null;
	
	private static final int maxPlayers = 4;
	private int numPlayers;
	
	private PlayersContainer() {
		players = new Player[maxPlayers];
	}
	
	public static PlayersContainer getInstance() {
		if (instance == null) {
			instance = new PlayersContainer();
		}
		return instance;
	}
	
	public boolean addPlayer(Player player) {
		if (numPlayers < maxPlayers) {
			players[numPlayers] = player;
			numPlayers += 1;
			return true;
		}
		return false;
	}
	
	public Player getPlayer(int player) {
		return players[player];
	}
}
