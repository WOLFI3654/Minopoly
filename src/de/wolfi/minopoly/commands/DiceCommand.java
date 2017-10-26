package de.wolfi.minopoly.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.scheduler.BukkitTask;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.events.DiceEvent;
import de.wolfi.minopoly.utils.Messages;
import de.wolfi.utils.ItemBuilder;
import de.wolfi.utils.TitlesAPI;
import io.netty.util.internal.ThreadLocalRandom;

public class DiceCommand extends CommandInterface {
	public DiceCommand(Main plugin) {
		super(plugin, 0, true);
	}

	private static final Main MAIN = Main.getMain();

	private static final ArrayList<DiceRunnable> scheds = new ArrayList<>();

	private static class DiceRunnable implements Runnable {
		private BukkitTask task;
		private Player player;
		private short selected_slot = 0;
		private short first = 0;

		private DiceRunnable() {
		}

		@Override
		public void run() {
			player.getHook().playSound(player.getHook().getLocation(), Sound.CLICK, 1f, 1f);

			short dur = (short) (ThreadLocalRandom.current().nextInt(6));
			this.selected_slot = dur;
			TitlesAPI.sendFullTitle(this.player.getHook(), 0, 10, 0, "§" + String.valueOf(11 % dur) + "Würfel:",
					"§" + String.valueOf(dur % 10) + (dur + 1));
			this.player.getHook().getInventory().setItem(this.player.getHook().getInventory().getHeldItemSlot(),
					new ItemBuilder(Material.INK_SACK).setName("§6Zahl: §a" + (dur + 1)).setMeta((short) dur).build());
			try {
				Thread.sleep(120);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		/*@Override

		public void run() {

			player.getHook().getInventory().setHeldItemSlot(++selected_slot+DiceCommand.SLOT_OFFSET);

			if(selected_slot >= 6) selected_slot = 0;

		}*/

		public void remove() {
			task.cancel();
			DiceCommand.scheds.remove(this);

		}

		public int getFirst() {
			return first;
		}

		public short getValue() {
			return selected_slot;
		}
	}

	@EventHandler
	public void onSlotChange(PlayerItemHeldEvent e){
		DiceRunnable dice = this.getSched(e.getPlayer());
		if(dice != null) e.setCancelled(true);
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		DiceRunnable dice = this.getSched(e.getPlayer());
		if (e.getAction() != Action.PHYSICAL & dice != null) {

			if (dice.getFirst() == 0) {
				dice.first = dice.getValue();
			} else {
				dice.remove();

				int first = dice.getFirst();
				int second = dice.getValue();
				DiceEvent event = new DiceEvent(dice.player, first, second);
				Bukkit.getPluginManager().callEvent(event);
				Messages.PLAYER_ROLLED_THE_DICE.broadcast(dice.player.getName(), String.valueOf(event.getOne()),
						String.valueOf(event.getTwo()));

			}
		}
	}

	private DiceRunnable getSched(org.bukkit.entity.Player player) {
		DiceRunnable dicing = null;
		for (DiceRunnable r : DiceCommand.scheds)
			if (r.player.getHook().getUniqueId().equals(player.getUniqueId()))
				dicing = r;
		return dicing;
	}

	@Override
	protected void executeCommand(Minopoly board, Player player, String[] args) {
		DiceRunnable dice = new DiceRunnable();
		dice.player = player;
		dice.task = Bukkit.getScheduler().runTaskTimer(DiceCommand.MAIN, dice, 3, 1);
		scheds.add(dice);

	}

}
