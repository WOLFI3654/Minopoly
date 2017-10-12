package de.wolfi.minopoly.components;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;

import de.wolfi.minopoly.utils.Messages;
import de.wolfi.utils.Scoreboard;

public class ScoreboardManager extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7018585926573683540L;

	private transient Scoreboard scoreboard;

	@Override
	protected void load() {
		this.scoreboard = new Scoreboard(Messages.Prefix, DisplaySlot.SIDEBAR);
	}

	protected void addPlayer(Player player) {
		this.scoreboard.addPlayer(player.getHook());
		this.updatePlayer(player);
	}

	protected void updatePlayer(Player player) {
		this.scoreboard.setScore(ChatColor.GREEN + player.getFigure().getName(), player.getMoney());
	}

	protected void setValue(String key, int value){
		this.scoreboard.setScore(ChatColor.RED+key, value);
	}
	
	@Override
	protected void unload() {
	}

}
