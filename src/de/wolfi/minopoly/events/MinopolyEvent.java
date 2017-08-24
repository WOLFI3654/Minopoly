package de.wolfi.minopoly.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.wolfi.minopoly.components.Player;

public class MinopolyEvent extends Event{

	protected Player player;
	public MinopolyEvent(Player player) {
		this.player = player;
	}
	
	private static final HandlerList list = new HandlerList();
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return list;
	}
	
	public Player getPlayer() {
		return player;
	}

	
}
