package de.wolfi.minopoly.components.minigames;

import java.util.HashMap;

import org.bukkit.Location;

import de.wolfi.minopoly.components.GameObject;
import de.wolfi.minopoly.utils.Dangerous;

public class Minigame extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -948671377374764648L;

	
	private final String hookClazz = "de.wolfi.minopoly.components.minigames.MinigameHook";
	private transient Location location;
	private String name = "Unknown Minigame";
	private String addPlayer = "say adding $p to " + this.name;
	private String start = "start " + this.name;
	private final HashMap<String, Object> storedLocation;
	private boolean supportHook = false;

	public Minigame(Location spawn, boolean supportHook) {
		this.storedLocation = new HashMap<>(spawn.serialize());
		this.location = spawn;
		this.supportHook = supportHook;
	}

	public String getAddPlayer() {
		return this.addPlayer;
	}

	public Location getLocation() {
		return this.location;
	}

	public String getName() {
		return this.name;
	}

	public String getStart() {
		return this.start;
	}

	public boolean isHookSupported() {
		return this.supportHook;
	}

	@Dangerous(y="Internal use ONLY!")
	public void load() {
		this.location = Location.deserialize(this.storedLocation);
	}

	public void setAddPlayer(String addPlayer) {
		this.addPlayer = addPlayer;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStart(String start) {
		this.start = start;
	}

	@Dangerous(y="Internal use ONLY!")
	@Override
	public void unload() {
		//XXX
	}

}
