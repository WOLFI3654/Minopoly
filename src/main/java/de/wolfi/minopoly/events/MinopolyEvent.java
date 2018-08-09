package de.wolfi.minopoly.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.wolfi.minopoly.components.Player;

public class MinopolyEvent extends Event implements Cancellable{

	private boolean cancelled = false;
	protected Player player;
	public MinopolyEvent(Player player) {
		this.player = player;
	}
	
	private static final HandlerList list = new HandlerList();
	@Override
	public HandlerList getHandlers() {
		return list;
	}
	
	public static HandlerList getHandlerList() {
		return list;
	}
	
	
	public Player getPlayer() {
		return player;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancelled = arg0;
	}

	
}
