package de.wolfi.minopoly.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;

public class MoveCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender paramCommandSender, Command arg1, String arg2, String[] args) {
		final org.bukkit.entity.Player sender = (org.bukkit.entity.Player) paramCommandSender;
		if (!Main.getMain().isMinopolyWorld(sender.getWorld())) {
			sender.sendMessage("command.wrongworld");
			return true;
		}
		final Minopoly game = Main.getMain().getMinopoly(sender.getWorld());
		if (args.length < 2) {
			paramCommandSender.sendMessage("args.missing");
			return true;
		}
		final org.bukkit.entity.Player playername = Bukkit.getPlayer(args[0]);
		if (playername == null) {
			sender.sendMessage("player.missing");
			return true;
		}
		final Player p = game.getByBukkitPlayer(playername);
		if (p == null) {
			sender.sendMessage("player.missing.game");
			return true;
		}
		int steps = 0;
		try {
			steps = Integer.parseInt(args[1]);
		} catch (final Exception e) {
			sender.sendMessage(ChatColor.DARK_RED + e.getMessage());
		}
		p.move(steps);
		return false;
	}

}
