package de.wolfi.minopoly.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Messages;

public class BankCommand extends CommandInterface {

	public BankCommand(Main plugin) {
		super(plugin, 3, false);
	}

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

		return true;
	}

	@Override
	public List<String> onTabComplete(final CommandSender paramCommandSender, final Command paramCommand,
			final String paramString, final String[] paramArrayOfString) {
		// TODO
		return null;
	}

	@Override
	protected void executeCommand(Minopoly board, Player player, String[] args) {
		final int amount = Integer.parseInt(args[1]);
		final String money = args[1];
		final String reason = args[2];
		switch (args[0]) {
		case "pay": {

			player.addMoney(amount, reason);
			Messages.MONEY_GLOBAL_PAID.send(board, money, player.getDisplay());
		}
			break;
		case "get": {
			player.removeMoney(amount, reason);
			Messages.MONEY_GLOBAL_GOT.send(board, money, player.getDisplay(), reason);
		}
			break;
		case "move": {
			if (args.length < this.getMinArgs() + 1) {
				Messages.COMMAND_NO_ARGUMENTS.send(board);
				return;
			}
			final Player pr = board.getByPlayerName(args[3]);
			if (pr == null) {
				Messages.COMMAND_NO_PLAYER.send(board, args[3]);
				return;
			}
			player.transferMoneyTo(pr, amount, reason);
			Messages.MONEY_GLOBAL_TRANSFER.send(board, money, player.getDisplay(), pr.getDisplay(), reason);
		}
			break;
		default:
			break;
		}
	}

}
