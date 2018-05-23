package de.wolfi.minopoly.events;

import de.wolfi.minopoly.components.Player;

public class MoveFinishedEvent extends MinopolyEvent{

	public MoveFinishedEvent(Player player) {
		super(player);
	}

}
