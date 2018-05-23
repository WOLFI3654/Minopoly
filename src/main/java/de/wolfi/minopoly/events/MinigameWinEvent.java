package de.wolfi.minopoly.events;

import de.wolfi.minopoly.components.Player;

public class MinigameWinEvent extends MinopolyEvent{

	public MinigameWinEvent(Player player) {
		super(player);
	}

}
