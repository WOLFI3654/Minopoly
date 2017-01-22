package de.wolfi.minopoly.components;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.HandlerList;

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
	
	private transient GameListener listener;
	
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
		
		listener = new GameListener();
	}
	
	public void unload(){
		HandlerList.unregisterAll(listener);
	}
	
	public World getWorld(){
		return Bukkit.getWorld(world);
	}
	
	public String getWorldName() {
		return world;

	}
	
	public @Nullable Player getByBukkitPlayer(org.bukkit.entity.Player player){
		for(Player p : playingPlayers){
			if(p.getHook().equals(player)){
				return p;
			}
		}
		return null;
	}
	
	
	public void addField(Field f){
		f.spawn();
		fields.add(f);
	}
	
	public Field getNextField(Field from){
		boolean next = false;
		for(Field f : fields){
			if(f.equals(from)){
				next = true;
				continue;
			}
			if(next){
				return from;
			}
		}
		if(!next) return null;
		return fields.get(0);
	}
	
}

