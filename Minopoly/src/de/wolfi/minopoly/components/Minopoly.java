package de.wolfi.minopoly.components;

import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.wolfi.minopoly.components.fields.Field;

public class Minopoly implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7587244395086533643L;

	private final ArrayList<Field> fields = new ArrayList<>();
	private final Bank bank = new Bank();
	private final MinigameManager mgManager = new MinigameManager();
	
	private final ArrayList<SerializeablePlayer> savedPlayers = new ArrayList<>();
	
	private transient ArrayList<Player> playingPlayers = new ArrayList<>();
	
	private final String world;
	
	public Minopoly(String world) {
		this.world = world;
	}
	

	public void load(){
		bank.load();
		for(Field f : fields){
			f.load();
		}
		mgManager.load();
	}
	
	public World getWorld(){
		return Bukkit.getWorld(world);
	}
	
	public String getWorldName() {
		return world;

	}
	
}

