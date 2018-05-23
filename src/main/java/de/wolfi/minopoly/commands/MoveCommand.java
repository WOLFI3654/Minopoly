package de.wolfi.minopoly.commands;

import org.bukkit.ChatColor;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;

public class MoveCommand extends CommandInterface {

	public MoveCommand(Main plugin) {
		super(plugin, 1, false);
		
	}
	
	@Override
	protected void executeCommand(Minopoly board, Player player, String[] args) {
		int steps = 0;
		try {
			steps = Integer.parseInt(args[0]);
		} catch (final Exception e) {
			board.sendMessage(ChatColor.DARK_RED + e.getMessage());
		}
		player.move(steps);
	}

}
