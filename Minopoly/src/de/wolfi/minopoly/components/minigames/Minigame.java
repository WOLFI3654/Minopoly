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
	
}
