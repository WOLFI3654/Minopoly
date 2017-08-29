package de.wolfi.minopoly.events;

import de.wolfi.minopoly.components.Player;

public class MinigameFoundEvent extends MinopolyEvent{

	public MinigameFoundEvent(Player player) {
		super(player);
	}

}
