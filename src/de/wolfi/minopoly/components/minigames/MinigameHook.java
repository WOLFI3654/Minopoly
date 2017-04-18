package de.wolfi.minopoly.components.minigames;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.bukkit.event.Listener;

import de.wolfi.minopoly.components.Player;
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

	public void removePlayer(Player player) {
		this.players.remove(player);
	}

	public abstract void start();;

	protected final void win(Player... players) {
		final StringBuilder string = new StringBuilder(players[0].getName());
		for (int i = 1; i < players.length; i++) {
			string.append(i == players.length - 1 ? " und " : ", ");
			string.append(players[i].getName());
		}
		Messages.MINIGAME_WIN.broadcast(this.getName(), string.toString());
	}
	
	protected abstract void init();

}
