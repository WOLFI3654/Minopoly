package de.wolfi.minopoly.components.fields;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Messages;

public abstract class Field implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2119752416278230984L;

	
	private static final HashMap<FieldColor,ArrayList<Field>> members = new HashMap<>();
	private final FieldColor color;
	
	private boolean isOwned = false;
	private Player owner;


	private final String name;
	
	private final HashMap<String, Object> storedLocation;
	private transient Location location;


	protected final Minopoly game;
	
	
	private static final void add(FieldColor color2, Field field) {
		ArrayList<Field> l = members.get(color2);
		if(l == null) l = new ArrayList<>();
		if(!l.contains(field))l.add(field);
		members.put(color2, l);
	}

	
	public Field(String name, FieldColor color, Location l, Minopoly game) {
		this.color = color;
		this.name = name;
		this.storedLocation = new HashMap<>(l.serialize());
		this.location = l;
		this.game = game;
		add(color, this);
	}
	

	public FieldColor getColor() {
		return color;
	}
	
	
	public boolean isOwned() {
		return isOwned;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public void playerStand(Player player){
		if(isOwned()){
			if(!owner.equals(player)){
				Messages.OTHER_FIELD_ENTERED.broadcast(player.getName(),owner.getName(),color.getColorChat()+getName());
			}
		}
	}
	
	public abstract void byPass(Player player);


	public void load(){
		location = Location.deserialize(storedLocation);
	}
	
	private String getName() {
		return name;
	}

	public Location getLocation() {
		return location;
	}
	
	public World getWorld(){return location.getWorld();}
	
}
