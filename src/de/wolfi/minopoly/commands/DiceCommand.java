package de.wolfi.minopoly.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Messages;

public class DiceCommand extends CommandInterface {
	public DiceCommand(Main plugin) {
		super(plugin, 0, true);
	}

	private static final Main MAIN = Main.getMain();
	
	private static final ArrayList<DiceRunnable> scheds = new ArrayList<>();
	private static final int SLOT_OFFSET = 2;
	private static class DiceRunnable implements Runnable{
		private BukkitTask task;
		private Player player;
		private int selected_slot = 0;
		private DiceRunnable(){}
		@Override
		public void run() {
			player.getHook().getInventory().setHeldItemSlot(++selected_slot+DiceCommand.SLOT_OFFSET);
			if(selected_slot >= 6) selected_slot = 0;
		}
		public void remove() {
			task.cancel();
			DiceCommand.scheds.remove(this);
			
		}
		public int getValue(){
			return selected_slot;
		}
	}
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		DiceRunnable dice = this.getSched(e.getPlayer());
		if(e.getAction() != Action.PHYSICAL & dice != null){
			Messages.PLAYER_ROLLED_THE_DICE.broadcast(dice.player.getName(),String.valueOf(dice.getValue()));
			dice.remove();
		}
	}

	private DiceRunnable getSched(org.bukkit.entity.Player player) {
		DiceRunnable dicing = null;
		for(DiceRunnable r : DiceCommand.scheds) if(r.player.getHook().getUniqueId().equals(player.getUniqueId())) dicing= r;
		return dicing;
	}

	@Override
	protected void executeCommand(Minopoly board, Player player, String[] args) {
		DiceRunnable dice = new DiceRunnable();
		dice.player = player;
		dice.task = Bukkit.getScheduler().runTaskTimer(DiceCommand.MAIN, dice, 3, 3);
		scheds.add(dice);
		
	}

}
