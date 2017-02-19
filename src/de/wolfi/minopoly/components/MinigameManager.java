package de.wolfi.minopoly.components;

import java.io.Serializable;
import java.util.ArrayList;

import de.wolfi.minopoly.components.minigames.Minigame;

public class MinigameManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6148489128474652079L;

	private final ArrayList<Minigame> minigames = new ArrayList<>();

	public void load() {
		for (final Minigame minigame : this.minigames)
			minigame.load();

	}

}
