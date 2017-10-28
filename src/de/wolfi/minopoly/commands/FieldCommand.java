package de.wolfi.minopoly.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.components.fields.Field;
import de.wolfi.minopoly.components.fields.NormalField;
import de.wolfi.utils.ItemBuilder;
import de.wolfi.utils.inventory.InventoryCounter;

public class FieldCommand extends CommandInterface implements InventoryHolder {

	public static final ItemStack fieldGUI = new ItemBuilder(Material.WATER_LILY).setName("§aFelder Managen").build();

	private final String title = "Felder Managen";

	private static final Enchantment owned = new ItemBuilder.MyEnchantment("Owned");

	public FieldCommand(Main plugin) {
		super(plugin, 1, true);
	}

	@Override
	public List<String> onTabComplete(final CommandSender paramCommandSender, final Command paramCommand,
			final String paramString, final String[] paramArrayOfString) {
		// TODO
		return null;
	}

	@Override
	protected void executeCommand(Minopoly board, Player player, String[] args) {
		switch (args[0]) {
		case "gui": {
			Inventory inv = this.getInventory();
			for (Field f : board.getFieldManager().getFields()) {
				MaterialData data = f.getBlock();
				ItemBuilder field = new ItemBuilder(data.toItemStack());
				field.setName(f.toString());
				field.addLore("Typ: "+f.getClass().getSimpleName());
				if(f instanceof NormalField) field.addLore("Farbe: "+f.getColor().toString());
				if(f.getPrice() >= 0){
					field.addLore("Preis: "+f.getPrice());
					field.addLore("Steuern: "+f.getBilling());
				}
				if(f.isOwned()) field.addLore("Owner: "+f.getOwner().getDisplay());
				if (f.isOwnedBy(player))
					field.enchant(owned, 10);
				inv.addItem(field.build());
			}
			player.getHook().openInventory(inv);
		}
		break;
		case "buy":
			if(!player.getLocation().isOwned()) player.getLocation().buy(player);
			break;
		case "sell":
			if(player.getLocation().isOwnedBy(player)) player.getLocation().sell();
			break;
		case "move":
			Player pr = board.getByPlayerName(args[1]);
			if(player.getLocation().isOwnedBy(player)) player.getLocation().moveProperty(pr);
			break;
		default:
			break;
		}
	}

	@EventHandler
	public void onItemUse(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR
				|| e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem() != null) {
			if (e.getItem().equals(FieldCommand.fieldGUI)) {
				e.setCancelled(true);
				Bukkit.dispatchCommand(Main.getMain().getMinopoly(e.getPlayer().getWorld()),
						"field " + e.getPlayer().getName() + " gui");
			}
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory().getHolder() == this) {
			e.setCancelled(true);
			if (e.getCurrentItem() != null) {

				Minopoly game = Main.getMain().getMinopoly(e.getWhoClicked().getWorld());
				InventoryCounter counter = new InventoryCounter(
						">" + e.getCurrentItem().getItemMeta().getDisplayName());
				counter.setCallback((c) -> {
					int amount = c.getAmount();

					if (e.getCurrentItem().getType() == Material.SKULL_ITEM)
						game.getByBukkitPlayer((org.bukkit.entity.Player) e.getWhoClicked()).transferMoneyTo(
								game.getByPlayerName(((SkullMeta) e.getCurrentItem().getItemMeta()).getOwner()), amount,
								"DirektPay");
					else if (e.getCurrentItem().getType() == Material.FLOWER_POT_ITEM) {
						game.getByBukkitPlayer((org.bukkit.entity.Player) e.getWhoClicked()).removeMoney(amount,
								"DirektPay: POTT");
						game.getTHE_POTT_OF_DOOM___andmore_cute_puppies().addMoney(amount);
					}
					counter.destroy();
					return true;
				});
				counter.open((org.bukkit.entity.Player) e.getWhoClicked());

			}
		}
	}

	@Override
	public Inventory getInventory() {
		return Bukkit.createInventory(this, 9*6, title);
	}

}
