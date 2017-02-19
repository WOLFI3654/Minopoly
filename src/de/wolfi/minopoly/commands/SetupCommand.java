package de.wolfi.minopoly.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.fields.CommunityField;
import de.wolfi.minopoly.components.fields.EventField;
import de.wolfi.minopoly.components.fields.JailField;
import de.wolfi.minopoly.components.fields.PoliceField;
import de.wolfi.minopoly.components.fields.StartField;
import de.wolfi.utils.ItemBuilder;
import de.wolfi.utils.nms.AnvilGUI;
import de.wolfi.utils.nms.AnvilGUI.AnvilSlot;

public class SetupCommand implements CommandExecutor, Listener {

	/**
	 * Field Setup Items
	 */
	private static final ItemStack field_setup = new ItemBuilder(Material.WOOD_PLATE).setName("§eFields")
			.addLore("< Zurück").build();
	private static final ItemStack field_setup_colorer = new ItemBuilder(Material.INK_SACK).setName("§eColor")
			.addLore("# Gib deiner Straße eine schöne Farbe").build();
	private static final ItemStack field_setup_renamer = new ItemBuilder(Material.ANVIL).setName("§eName")
			.addLore("> Benenne deine Straße").build();

	private static final ItemStack fieldtype_communityField = new ItemBuilder(Material.GOLD_PLATE)
			.setName("§7Community Feld").build();
	private static final ItemStack fieldtype_eventField = new ItemBuilder(Material.IRON_PLATE).setName("§Event Feld")
			.build();
	private static final ItemStack fieldtype_jailField = new ItemBuilder(Material.IRON_BARDING).setName("§7Jelly Feld")
			.build();
	private static final ItemStack fieldtype_normalField = new ItemBuilder(Material.WOOD_PLATE)
			.setName("§7Normales Feld").build();
	private static final ItemStack fieldtype_policeField = new ItemBuilder(Material.IRON_SWORD)
			.setName("§7Polizei Feld").build();

	// --------------------------------------------

	private static final ItemStack fieldtype_startField = new ItemBuilder(Material.BANNER).setName("§7Start Feld")
			.build();

	// --------------------------------------------

	private static final InventoryHolder holder = () -> SetupCommand.minopolyChooser;
	private static final Main main = Main.getMain();
	// -------------------------------------------
	/**
	 * Main Setup Menu
	 */
	private static final ItemStack main_setup = new ItemBuilder(Material.SAPLING).setName("§aMain Setup")
			.addLore("< Zurück").build();
	private static final ItemStack main_setup_fieldItem = new ItemBuilder(Material.WOOD_PLATE).setName("§eFields")
			.addLore("> Füge Felder hinzu").build();
	private static final ItemStack main_setup_minigameItem = new ItemBuilder(Material.EMERALD).setName("§eMinigames")
			.addLore("> Minigame Verwaltung").build();
	// -------------------------------------------
	/**
	 * Main Minigame Menu
	 */

	private static final ItemStack minigame_main = new ItemBuilder(Material.CHEST).setName("§aMinigame Übersicht")
			.addLore("< Zurück").build();
	/**
	 * Minigame Setup Menu
	 */

	private static final ItemStack minigame_setup = new ItemBuilder(Material.EMERALD).setName("§aMinigame Setup")
			.addLore("< Zurück").build();
	
	
	private static final Inventory minopolyChooser;
	private static final HashMap<Player, Minopoly> setups = new HashMap<>();

	// --------------------------------------------

	static {
		minopolyChooser = Bukkit.createInventory(SetupCommand.holder, 9 * 5, "§cWähle eine Welt / Setup");
		for (final World w : Bukkit.getWorlds()) {
			final ItemBuilder i = new ItemBuilder(Material.SPONGE);
			i.setName(w.getName());
			if (!SetupCommand.main.isMinopolyWorld(w))
				i.addLore("Not setup yet!");

			SetupCommand.minopolyChooser.addItem(i.build());
		}
	}

	public SetupCommand() {
		Bukkit.getPluginManager().registerEvents(this, SetupCommand.main);

	}

	private Inventory createFieldSetup(Minopoly m) {
		final Inventory inv = Bukkit.createInventory(SetupCommand.holder, 9, "§c" + m.getWorldName() + " - Field");
		inv.setItem(0, SetupCommand.field_setup);
		inv.setItem(1, SetupCommand.field_setup_renamer);
		inv.setItem(2, SetupCommand.field_setup_colorer);
		return inv;

	}

	private Inventory createGameManagmentInventory(Minopoly m) {
		final Inventory inv = Bukkit.createInventory(SetupCommand.holder, InventoryType.HOPPER,
				"§c" + m.getWorldName() + " - Setup");
		inv.setItem(0, SetupCommand.main_setup);
		inv.setItem(1, SetupCommand.main_setup_fieldItem);
		inv.setItem(2, SetupCommand.main_setup_minigameItem);

		return inv;
	}

	private void giveFieldSetupItems(HumanEntity whoClicked) {
		whoClicked.getInventory().addItem(SetupCommand.fieldtype_normalField, SetupCommand.fieldtype_eventField,
				SetupCommand.fieldtype_communityField, SetupCommand.fieldtype_policeField,
				SetupCommand.fieldtype_jailField, SetupCommand.fieldtype_startField);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		final ItemStack clicked = e.getCurrentItem();
		if (clicked == null)
			return;
		if (e.getClickedInventory().getHolder() != SetupCommand.holder)
			return;

		if (e.getClickedInventory() == SetupCommand.minopolyChooser) {

			final World w = Bukkit.getWorld(clicked.getItemMeta().getDisplayName());
			if (w == null)
				Bukkit.broadcastMessage("§cError while converting world");
			final Minopoly m = SetupCommand.main.loadMap(w);
			SetupCommand.setups.put((Player) e.getWhoClicked(), m);
			e.getWhoClicked().openInventory(this.createGameManagmentInventory(m));
		} else {
			final ItemStack checker = e.getClickedInventory().getItem(0);
			if (checker.equals(SetupCommand.main_setup)) {
				if (clicked.equals(SetupCommand.main_setup_fieldItem))
					this.giveFieldSetupItems(e.getWhoClicked());
			} else if(checker.equals(SetupCommand.field_setup)){
				if(clicked.equals(SetupCommand.field_setup_renamer)){
					AnvilGUI gui = new AnvilGUI((Player) e.getWhoClicked(),(event)->{new ItemBuilder(clicked).setName(event.getName());});
					gui.setSlot(AnvilSlot.INPUT_LEFT, field_setup_renamer);
					gui.open("RENAME YOUR STREET");
				} else if(clicked.equals(SetupCommand.field_setup_colorer)){
					
				}
			} else if (checker.equals(SetupCommand.minigame_main)) {

			} else if (checker.equals(SetupCommand.minigame_setup)) {

			} else
				e.getWhoClicked().sendMessage("§cError while converting inventory");
		}
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player)
			((Player) arg0).openInventory(SetupCommand.minopolyChooser);
		return true;
	}

	/**
	 * This method wil create Field
	 * 
	 * @category EventHandling
	 * @param e
	 *            Event Argument
	 */
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getItem() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			final Minopoly m = SetupCommand.setups.get(e.getPlayer());
			if (e.getItem().equals(SetupCommand.fieldtype_normalField))
				e.getPlayer().openInventory(this.createFieldSetup(m));
			else if (e.getItem().equals(SetupCommand.fieldtype_eventField))
				m.addField(new EventField(e.getClickedBlock().getLocation(), m));
			else if (e.getItem().equals(SetupCommand.fieldtype_communityField))
				m.addField(new CommunityField(e.getClickedBlock().getLocation(), m));
			else if (e.getItem().equals(SetupCommand.fieldtype_startField))
				m.addField(new StartField(e.getClickedBlock().getLocation(), m));
			else if (e.getItem().equals(SetupCommand.fieldtype_policeField))
				m.addField(new PoliceField(e.getClickedBlock().getLocation(), m));
			else if (e.getItem().equals(SetupCommand.fieldtype_jailField))
				m.addField(new JailField(e.getClickedBlock().getLocation(), m));
		}
	}

}
