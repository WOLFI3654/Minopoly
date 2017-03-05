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
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import de.wolfi.minopoly.utils.FigureType;

public class Minopoly implements Serializable, CommandSender {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7587244395086533643L;

	private final Bank bank = new Bank();
	private transient GameListener listener;

	private final FieldManager fdManager = new FieldManager();
	private final MinigameManager mgManager = new MinigameManager();

	private transient ArrayList<Player> playingPlayers = new ArrayList<>();

	private final ArrayList<SerializeablePlayer> savedPlayers = new ArrayList<>();

	private final String world;

	private final int size;
	public Minopoly(String world) {
		this.world = world;
		this.size = FigureType.values().length;
		this.createPlayers();
	}

	private void createPlayers() {
		for(int i = 0; i < size; i++){
			SerializeablePlayer p = new SerializeablePlayer(null, FigureType.values()[i], bank.checkIn());
			this.savedPlayers.add(p);
		}
		
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

	

	public @Nullable Player getByBukkitPlayer(org.bukkit.entity.Player player) {
		for (final Player p : this.playingPlayers)
			if (p.getHook().equals(player))
				return p;
		return null;
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return null;
	}

	@Override
	public String getName() {
		return this.toString();
	}

	

	@Override
	public Server getServer() {
		return Bukkit.getServer();
	}

	public World getWorld() {
		return Bukkit.getWorld(this.world);
	}

	public String getWorldName() {
		return this.world;

	}

	@Override
	public boolean hasPermission(Permission arg0) {
		return true;
	}

	@Override
	public boolean hasPermission(String arg0) {
		return true;
	}

	@Override
	public boolean isOp() {
		return true;
	}

	@Override
	public boolean isPermissionSet(Permission arg0) {
		return true;
	}

	@Override
	public boolean isPermissionSet(String arg0) {
		return true;
	}

	public void load() {
		this.bank.load();
		this.fdManager.load();
		this.mgManager.load();

		this.listener = new GameListener();
	}

	public FieldManager getFieldManager() {
		return fdManager;
	}
	@Override
	public void recalculatePermissions() {
	}

	@Override
	public void removeAttachment(PermissionAttachment arg0) {
	}

	@Override
	public void sendMessage(String arg0) {
		Bukkit.broadcastMessage(arg0);
	}

	@Override
	public void sendMessage(String[] arg0) {
		for (final String msg : arg0)
			Bukkit.broadcastMessage(msg);

	}

	@Override
	public void setOp(boolean arg0) {
	}

	public void unload() {
		HandlerList.unregisterAll(this.listener);
	}

}
