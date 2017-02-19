package de.wolfi.minopoly.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import de.wolfi.minopoly.components.fields.Field;

public class Minopoly implements Serializable, CommandSender{

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


	
	
	
	@Override
	public PermissionAttachment addAttachment(Plugin arg0) {
		return null;
	}


	@Override
	public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
		return null;
	}


	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
		return null;
	}


	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
		return null;
	}


	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return null;
	}


	@Override
	public boolean hasPermission(String arg0) {
		return true;
	}


	@Override
	public boolean hasPermission(Permission arg0) {
		return true;
	}


	@Override
	public boolean isPermissionSet(String arg0) {
		return true;
	}


	@Override
	public boolean isPermissionSet(Permission arg0) {
		return true;
	}


	@Override
	public void recalculatePermissions() {}


	@Override
	public void removeAttachment(PermissionAttachment arg0) {}


	@Override
	public boolean isOp() {
		return true;
	}


	@Override
	public void setOp(boolean arg0) {}


	@Override
	public String getName() {
		return toString();
	}


	@Override
	public Server getServer() {
		return Bukkit.getServer();
	}


	@Override
	public void sendMessage(String arg0) {
		Bukkit.broadcastMessage(arg0);
	}


	@Override
	public void sendMessage(String[] arg0) {
		for(String msg:arg0)Bukkit.broadcastMessage(msg);
		
	}
	
}

