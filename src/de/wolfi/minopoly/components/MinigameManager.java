package de.wolfi.minopoly.components;

import java.util.ArrayList;

import de.wolfi.minopoly.components.minigames.Minigame;

public class MinigameManager extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6148489128474652079L;

	private final ArrayList<Minigame> minigames = new ArrayList<>();

	@Override
	protected void load() {
		for (final Minigame minigame : this.minigames)
			minigame.load();

	}

	@Override
	protected void unload() {
		for (final Minigame minigame : this.minigames)
			minigame.unload();

	}

}
