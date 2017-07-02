package de.wolfi.minopoly.components;

import java.util.ArrayList;

import javax.annotation.Nullable;

import de.wolfi.minopoly.MinigameRegistry.MinigameStyleSheet;
import de.wolfi.minopoly.components.minigames.Minigame;
import io.netty.util.internal.ThreadLocalRandom;

public class MinigameManager extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6148489128474652079L;

	private final ArrayList<Minigame> minigames = new ArrayList<>();
	
	private Minigame currentGame = null;
	
	public void addMinigame(Minopoly game, MinigameStyleSheet sheet){
		this.minigames.add(new Minigame(game, sheet));
	}
	
	public void removeMinigame(MinigameStyleSheet mg) {
		this.minigames.remove(this.getMinigameOrNull(mg));
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

	public void playMinigame(Minopoly game, Minigame minigame){
		currentGame = minigame;
		for(Player p : game.getPlayingPlayers()){
			minigame.getMinigameHook().addPlayer(p);
		}
	}
	
	public Minigame randomMinigame(){
		return this.minigames.get(ThreadLocalRandom.current().nextInt(this.minigames.size()));
	}
	
	public boolean hasMinigame(MinigameStyleSheet sheet) {
		boolean is = false;
		for(Minigame mg : this.minigames)
			if(mg.isEquals(sheet))
				is = true;
		return is;
	}
	
	@Nullable
	public Minigame getMinigameOrNull(MinigameStyleSheet sheet){
		Minigame is = null;
		for(Minigame mg : this.minigames)
			if(mg.isEquals(sheet))
				is = mg;
		return is;

	}
	
	@Nullable
	public Minigame getCurrentGame() {
		return currentGame;
	}

}
