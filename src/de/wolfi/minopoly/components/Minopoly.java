package de.wolfi.minopoly.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import de.wolfi.minopoly.utils.Messages;

public class Minopoly extends GameObject implements CommandSender {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7587244395086533643L;

	private final Bank bank = new Bank();
	
	private transient GameListener listener;
	private transient MainSettings settings;
	
	private final ScoreboardManager scoreboardManager = new ScoreboardManager();

	private final FieldManager fdManager = new FieldManager();
	private final MinigameManager mgManager = new MinigameManager();

	private transient ArrayList<Player> playingPlayers = new ArrayList<>();

	private final HashMap<FigureType, SerializeablePlayer> savedPlayers = new HashMap<>();
	private final HashMap<FigureType, Integer> jail = new HashMap<>();
	
	private final String world;

	private final int size;

	public Minopoly(String world) {
		this.world = world;
		this.size = FigureType.values().length;
		this.createPlayers();
	}

	private void createPlayers() {
		this.savedPlayers.clear();
		System.out.println("Create Players");
		for (int i = 0; i < size; i++) {
			SerializeablePlayer p = new SerializeablePlayer(this, this.getFieldManager().getStartField(), FigureType.values()[i], bank.checkIn());
			this.savedPlayers.put(FigureType.values()[i], p);
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

	public Collection<SerializeablePlayer> getFigures() {
		return savedPlayers.values();
	}

	public void selectPlayer(org.bukkit.entity.Player player, FigureType sPlayer) {
		Player toUpdate = null;
		for (Player p : playingPlayers) {
			if (p.getHook().getUniqueId().equals(player.getUniqueId()))
				toUpdate = p;
		}
		SerializeablePlayer ply = this.getBySPFigureType(sPlayer);
		if (toUpdate != null) {
			this.getBySPFigureType(toUpdate.getFigure()).setSelected(false);
			toUpdate.update(ply);
		} else {
			Player newP = new Player(player, sPlayer, this, this.bank);
			this.bank.checkIn(newP, ply.getBankCard());
			this.playingPlayers.add(newP);
			if(ply.getLoc() == null) newP.teleport(this.fdManager.getStartField());
			else newP.teleport(ply.getLoc());
			toUpdate = newP;
		}
		ply.setSelected(true);
		scoreboardManager.addPlayer(toUpdate);
		Messages.FIGURE_SELECTED.broadcast(player.getName(), sPlayer.getDisplay());
	}

	public @Nullable Player getByFigureType(FigureType f) {
		for (final Player p : this.playingPlayers)
			if (p.getFigure() == f)
				return p;
		return null;
	}

	public @Nullable SerializeablePlayer getBySPFigureType(FigureType f) {
		return this.savedPlayers.get(f);
	}

	public @Nullable Player getByBukkitPlayer(org.bukkit.entity.Player player) {
		for (final Player p : this.playingPlayers)
			if (p.getHook().equals(player))
				return p;
		return null;
	}

	public @Nullable Player getByPlayerName(String player) {
		for (final Player p : this.playingPlayers)
			if (p.getName().equalsIgnoreCase(player))
				return p;
		return null;
	}
	
	public ArrayList<Player> getPlayingPlayers() {
		return playingPlayers;
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

	public Bank getBank() {
		return bank;
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

	@Override
	public void load() {
		this.settings = new MainSettings(getWorld());
		this.settings.load();
		
		this.playingPlayers = new ArrayList<>();
		this.bank.load();
		this.fdManager.load();
		this.mgManager.load();

		this.scoreboardManager.load();
		
		this.listener = new GameListener(this);
	}

	public FieldManager getFieldManager() {
		return fdManager;
	}
	
	public MinigameManager getMinigameManager() {
		return mgManager;
	}

	public ScoreboardManager getScoreboardManager() {
		return scoreboardManager;
	}
	
	public MainSettings getSettings() {
		return settings;
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
	
	
	
	public boolean isJailed(FigureType type){
		return this.jail.containsKey(type);
	}
	public void jailPlayer(FigureType type){
		this.jail.put(type, 3);
	}
	
	public void unjailPlayer(FigureType type){
		this.jail.remove(type);
	}
	
	public void jailRound(FigureType type){
		this.jail.put(type, this.jail.get(type)-1);
		if(this.jail.get(type) <= 0 ) this.unjailPlayer(type);
	}
	
	

	@Override
	public void unload() {
		for (Player player : this.playingPlayers) {
			this.savedPlayers.put(player.getFigure(), player.serialize());

		}
		this.scoreboardManager.unload();
		
		this.mgManager.unload();
		this.fdManager.unload();
		this.bank.unload();
		
		this.settings.unload();

		HandlerList.unregisterAll(this.listener);
	}

}
