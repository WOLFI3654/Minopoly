package de.wolfi.minopoly.commands;

import org.bukkit.Bukkit;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.events.NextPlayerEvent;

public class MinopolyCommand extends CommandInterface{

	public MinopolyCommand(Main plugin) {
		super(plugin, 1, false);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void executeCommand(Minopoly board, Player player, String[] args) {
		switch (args[0]) {
		case "start":
			for(Player p : board.getPlayingPlayers()) p.setInventory();
		case "next":
			Bukkit.getPluginManager().callEvent(new NextPlayerEvent());
			break;
		default:
			break;
		}
		
	}

}
