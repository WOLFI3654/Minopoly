package de.wolfi.minopoly.components.minigames;

import java.io.Serializable;
import java.util.HashMap;

import org.bukkit.Location;

public class Minigame implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -948671377374764648L;

	private boolean supportHook = false;
	private String hookClazz = "de.wolfi.minopoly.components.minigames.MinigameHook";
	private String name = "Unknown Minigame";
	private String addPlayer = "say adding $p to "+name;
	private String start = "start "+name;
	private final HashMap<String, Object> storedLocation;
	private transient Location location;

	public Minigame(Location spawn, boolean supportHook) {
		this.storedLocation = new HashMap<>(spawn.serialize());
		this.location = spawn;
		this.supportHook = supportHook;
	}
	
	public void load(){
		this.location = Location.deserialize(storedLocation);
	}
	
	public boolean isHookSupported() {
		return supportHook;
	}
	
	
	public Location getLocation() {
		return location;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAddPlayer(String addPlayer) {
		this.addPlayer = addPlayer;
	}
	
	public String getAddPlayer() {
		return addPlayer;
	}
	
	
	public void setStart(String start) {
		this.start = start;
	}
	
	public String getStart() {
		return start;
	}
	
	
}
