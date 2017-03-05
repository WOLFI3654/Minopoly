package de.wolfi.minopoly.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;

public class DiceCommand implements CommandExecutor, Listener {
	private static final Main MAIN = Main.getMain();
	
	private static final ArrayList<DiceRunnable> scheds = new ArrayList<>();
	private static final int SLOT_OFFSET = 2;
	private static class DiceRunnable implements Runnable{
		private BukkitTask task;
		private org.bukkit.entity.Player entity;
		private int selected_slot = 0;
		private DiceRunnable(){}
		@Override
		public void run() {
			entity.getInventory().setHeldItemSlot(++selected_slot+DiceCommand.SLOT_OFFSET);
			if(selected_slot > 6) selected_slot = 0;
		}
		public void remove() {
			task.cancel();
			DiceCommand.scheds.remove(this);
			
		}
	}
	
	public DiceCommand() {
		Bukkit.getPluginManager().registerEvents(this, DiceCommand.MAIN);

	}
	
	@Override
	public boolean onCommand(CommandSender paramCommandSender, Command arg1, String arg2, String[] args) {
		final org.bukkit.entity.Player sender = (org.bukkit.entity.Player) paramCommandSender;
		if (!Main.getMain().isMinopolyWorld(sender.getWorld())) {
			sender.sendMessage("command.wrongworld");
			return true;
		}
		final Minopoly game = Main.getMain().getMinopoly(sender.getWorld());
		if (args.length < 1) {
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
		DiceRunnable dice = new DiceRunnable();
		dice.entity = playername;
		dice.task = Bukkit.getScheduler().runTaskTimer(DiceCommand.MAIN, dice, 3, 3);
		scheds.add(dice);
		return true;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		DiceRunnable dice = this.getSched(e.getPlayer());
		if(e.getAction() != Action.PHYSICAL & dice != null){
			dice.remove();
		}
	}

	private DiceRunnable getSched(org.bukkit.entity.Player player) {
		DiceRunnable dicing = null;
		for(DiceRunnable r : DiceCommand.scheds) if(r.entity.getUniqueId().equals(player.getUniqueId())) dicing= r;
		return dicing;
	}

}
