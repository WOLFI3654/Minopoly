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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.components.fields.Field;
import de.wolfi.minopoly.components.fields.NormalField;
import de.wolfi.utils.ItemBuilder;
import de.wolfi.utils.inventory.InventoryConfirmation;

public class FieldCommand extends CommandInterface implements InventoryHolder {

	public static final ItemStack fieldGUI = new ItemBuilder(Material.WATER_LILY).setName("ßaFelder Managen").build();

	private final String title = "Felder Managen";

	private static final Enchantment owned = new ItemBuilder.MyEnchantment("Owned");

	private static final ItemStack sellItem = new ItemBuilder(Material.ARROW).setName("ßaVerkaufen").build();
	private static final ItemStack moveItem = new ItemBuilder(Material.PISTON_BASE).setName("ßaVerschicken").build();
	private static final ItemStack buyItem = new ItemBuilder(Material.PAPER).setName("ßaKaufen").build();

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

				inv.addItem(createFieldInfo(player, f));
			}
			player.getHook().openInventory(inv);
		}
			break;
		case "buy":
			if (!player.getLocation().isOwned())
				player.getLocation().buy(player);
			break;
		case "sell":
			if (player.getLocation().isOwnedBy(player))
				player.getLocation().sell();
			break;
		case "move":
			Player pr = board.getByPlayerName(args[1]);
			if (player.getLocation().isOwnedBy(player))
				player.getLocation().moveProperty(pr);
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

	public ItemStack createFieldInfo(Player p, Field f) {
		MaterialData data = f.getBlock();
		ItemBuilder field = new ItemBuilder(data.toItemStack());
		field.setName(f.toString());
		field.addLore("Typ: " + f.getClass().getSimpleName());
		if (f instanceof NormalField)
			field.addLore("Farbe: " + f.getColor().toString());
		if (f.getPrice() >= 0) {
			field.addLore("Preis: " + f.getPrice());
			field.addLore("Steuern: " + f.getBilling());
		}

		if (f.isOwned())
			field.addLore("Owner: " + f.getOwner().getDisplay());
		else
			field.addLore("ßaVerf¸gbar");
		if (f.isOwnedBy(p))
			field.enchant(owned, 10);
		return field.build();
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory().getHolder() == this) {
			e.setCancelled(true);
			if (e.getCurrentItem() != null) {
				if (FieldCommand.buyItem.isSimilar(e.getCurrentItem())) {
					InventoryConfirmation confirm = new InventoryConfirmation(
							"Mˆchtest du wirkich die Straﬂe " + e.getInventory().getTitle() + " verkaufen?");
					confirm.setCallback((i) -> {
						if (!confirm.isCancelled())
							Bukkit.dispatchCommand(e.getWhoClicked(), "field buy");
						return true;
					});
					confirm.open((org.bukkit.entity.Player) e.getWhoClicked());
				} else if (FieldCommand.sellItem.isSimilar(e.getCurrentItem())) {

				} else if (FieldCommand.moveItem.isSimilar(e.getCurrentItem())) {

				} else {
					Minopoly game = Main.getMain().getMinopoly(e.getWhoClicked().getWorld());
					Field f = game.getFieldManager().getFieldByString(null,
							e.getCurrentItem().getItemMeta().getDisplayName());
					Player p = game.getByBukkitPlayer((org.bukkit.entity.Player) e.getWhoClicked());
					e.getWhoClicked().openInventory(this.createFieldInv(p, f));
				}
			}
		}
	}

	private Inventory createFieldInv(Player player, Field f) {
		Inventory inv = Bukkit.createInventory(this, InventoryType.HOPPER, f.toString());
		inv.addItem(this.createFieldInfo(player, f));
		if (!f.isOwned())
			if (player.getLocation().equals(f))
				inv.addItem(FieldCommand.buyItem);
			else
				inv.addItem(new ItemBuilder(FieldCommand.buyItem).enchant(Enchantment.THORNS, 1)
						.addLore("ßaDu musst auf der Straﬂe stehen, um sie zu erwerben!").build());
		else if (f.isOwnedBy(player))
			inv.addItem(FieldCommand.sellItem, FieldCommand.moveItem);
		return inv;
	}

	@Override
	public Inventory getInventory() {
		return Bukkit.createInventory(this, 9 * 6, title);
	}

}
