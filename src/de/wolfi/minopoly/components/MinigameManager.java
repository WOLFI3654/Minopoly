package de.wolfi.minopoly.components;

import java.util.ArrayList;

import de.wolfi.minopoly.MinigameRegistry.MinigameStyleSheet;
import de.wolfi.minopoly.components.minigames.Minigame;

public class MinigameManager extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6148489128474652079L;

	private final ArrayList<Minigame> minigames = new ArrayList<>();

	public void addMinigame(){
		
	}
	
	public void removeMinigame(Minigame mg) {
		this.minigames.remove(mg);
	}
	
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

	public boolean hasMinigame(MinigameStyleSheet sheet) {
		boolean is = false;
		for(Minigame mg : this.minigames)
			if(mg.equals(sheet))
				is = true;
		return is;
	}

}
