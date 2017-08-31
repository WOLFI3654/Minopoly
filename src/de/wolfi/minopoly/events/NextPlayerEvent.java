package de.wolfi.minopoly.events;

import de.wolfi.minopoly.components.Player;

public class NextPlayerEvent extends MinopolyEvent{

	public NextPlayerEvent(Player player) {
		super(player);
	}

}
