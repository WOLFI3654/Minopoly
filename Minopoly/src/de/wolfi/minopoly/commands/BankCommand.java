package de.wolfi.minopoly.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class BankCommand implements TabExecutor{

	@Override
	public List<String> onTabComplete(CommandSender paramCommandSender, Command paramCommand, String paramString,
			String[] paramArrayOfString) {
		return null;
	}

	@Override
	public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString,
			String[] paramArrayOfString) {
		
		return true;
	}

}
