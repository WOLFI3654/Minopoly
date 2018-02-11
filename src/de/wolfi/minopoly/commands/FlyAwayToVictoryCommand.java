package de.wolfi.minopoly.commands;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.components.fields.Field;

public class FlyAwayToVictoryCommand extends CommandInterface implements InventoryHolder{

	public FlyAwayToVictoryCommand(Main plugin) {
		super(plugin, 0, true);
	}

	@Override
	protected void executeCommand(Minopoly board, Player player, String[] args) {
		Inventory inv = this.getInventory();
		for (Field f : board.getFieldManager().getFields()) {

			inv.addItem(board.getFieldManager().createFieldInfo(player, f));
		}
		player.getHook().openInventory(inv);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory().getHolder() == this) {
			e.setCancelled(true);
			if (e.getCurrentItem() != null) {
				Minopoly game = Main.getMain().getMinopoly(e.getWhoClicked().getWorld());
				Player p = game.getByBukkitPlayer((org.bukkit.entity.Player) e.getWhoClicked());
				
					Field f = game.getFieldManager().getFieldByString(null,
							e.getCurrentItem().getItemMeta().getDisplayName());
					p.teleport(f);
			}
		}
	}
	@Override
	public Inventory getInventory() {
		return Bukkit.createInventory(this, 9*3,"FlyAwayToVictory");
	}

}
