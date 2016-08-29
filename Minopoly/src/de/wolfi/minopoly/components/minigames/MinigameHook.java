package de.wolfi.minopoly.components.minigames;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.bukkit.event.Listener;

import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Messages;
import de.wolfi.minopoly.utils.TeleportCause;

public abstract class MinigameHook implements Listener{
	
	private final Minigame mg;
	public MinigameHook(Minigame mg) {
		this.mg = mg;
	}
	
	private final Vector<Player> players = new Vector<>(4, 2);
	
	public String getName() {return "Unknown Minigame";}
	
	public void addPlayer(Player player){players.add(player); player.teleport(mg.getLocation(), TeleportCause.MINIGAME_STARTED);}
	
	public void removePlayer(Player player){players.remove(player);}
	
	public abstract void start();
	
	public final List<Player> getPlayers(){return new ArrayList<>(players);};
	
	protected final void win(Player... players){
		StringBuilder string = new StringBuilder(players[0].getName());
		for(int i = 1; i < players.length; i++){
			string.append(i==players.length-1?" und ":", ");
			string.append(players[i].getName());
		}
		Messages.MINIGAME_WIN.broadcast(getName(),string.toString());
	}
	
}
