package de.wolfi.minopoly.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;

public class BankCommand implements TabExecutor {

	// bank pay *USER* *AMOUNT* *REASON*
	// bank get *USER* *AMOUNT* *REASON*
	// bank move *FROM* *TO* *AMOUNT* *REASON*
	@Override
	public boolean onCommand(final CommandSender paramCommandSender, final Command paramCommand,
			final String paramString, final String[] args) {
		if (paramCommandSender instanceof ConsoleCommandSender)
			return false;
		final org.bukkit.entity.Player sender = (org.bukkit.entity.Player) paramCommandSender;
		if (!Main.getMain().isMinopolyWorld(sender.getWorld())) {
			sender.sendMessage("command.wrongworld");
			return true;
		}
		final Minopoly game = Main.getMain().getMinopoly(sender.getWorld());
		if (args.length < 4) {
			paramCommandSender.sendMessage("args.missing");
			return true;
		}
		final org.bukkit.entity.Player playername = Bukkit.getPlayer(args[1]);
		if (playername == null) {
			sender.sendMessage("player.missing");
			return true;
		}
		final Player p = game.getByBukkitPlayer(playername);
		if (p == null) {
			sender.sendMessage("player.missing.game");
			return true;
		}
		switch (args[0]) {
		case "pay": {
			final int amount = Integer.parseInt(args[2]);
			p.addMoney(amount, args[3]);
			sender.sendMessage("bank.paid");
		}
			break;
		case "get": {
			final int amount = Integer.parseInt(args[2]);
			p.removeMoney(amount, args[3]);
			sender.sendMessage("bank.got");
		}
			break;
		case "move": {
			final org.bukkit.entity.Player rescn = Bukkit.getPlayer(args[1]);
			if (rescn == null) {
				sender.sendMessage("player.missing");
				return true;
			}
			final Player pr = game.getByBukkitPlayer(playername);
			if (pr == null) {
				sender.sendMessage("player.missing.game");
				return true;
			}
			final int amount = Integer.parseInt(args[2]);
			p.transferMoneyTo(pr, amount, args[3]);
			p.transferMoneyFrom(p, amount, args[3]);
			sender.sendMessage("bank.transfered");
		}
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(final CommandSender paramCommandSender, final Command paramCommand,
			final String paramString, final String[] paramArrayOfString) {
		return null;
	}

}
