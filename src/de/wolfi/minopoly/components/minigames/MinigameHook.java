package de.wolfi.minopoly.components.minigames;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.events.MinigameWinEvent;
import de.wolfi.minopoly.utils.Messages;
import de.wolfi.minopoly.utils.TeleportCause;

public abstract class MinigameHook implements Listener {

	private final Minigame mg;
	private final Vector<Player> players = new Vector<>(4, 2);

	public MinigameHook(Minigame mg) {
		this.mg = mg;
		this.init();
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
		player.teleport(this.mg.getLocation(), TeleportCause.MINIGAME_STARTED);
	}

	public String getName() {
		return this.mg.getName();
	}

	public final List<Player> getPlayers() {
		return new ArrayList<>(this.players);
	}
	
	public final boolean isPlaying(org.bukkit.entity.Player player){
		return this.players.contains(this.getBoard().getByBukkitPlayer(player));
	}
	
	public final boolean isPlaying(Player player){
		return this.players.contains(player);
	}

	public void removePlayer(Player player) {
		this.players.remove(player);
	}

	public abstract void start();

	public abstract void endGame();
	
	protected final Minopoly getBoard(){
		return this.mg.getBoard();
	}
	
	protected final Minigame getMinigame(){
		return mg;
	}
	
	protected final void win(Player... players) {
		final StringBuilder string = new StringBuilder(players[0].getName());
		for (int i = 1; i < players.length; i++) {
			string.append(i == players.length - 1 ? " und " : ", ");
			string.append(players[i].getName());
		}
		Bukkit.getPluginManager().callEvent(new MinigameWinEvent(players[0]));
		Messages.MINIGAME_WIN.broadcast(this.getName(), string.toString());
	}
	
	protected abstract void init();

}
