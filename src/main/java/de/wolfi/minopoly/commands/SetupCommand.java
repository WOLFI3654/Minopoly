package de.wolfi.minopoly.commands;

import java.util.HashMap;
import java.util.UUID;

import de.wolfi.minopoly.utils.nms1122.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
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
import de.wolfi.minopoly.MinigameRegistry;
import de.wolfi.minopoly.MinigameRegistry.MinigameStyleSheet;
import de.wolfi.minopoly.components.FieldManager;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.fields.AirportField;
import de.wolfi.minopoly.components.fields.CommunityField;
import de.wolfi.minopoly.components.fields.EventField;
import de.wolfi.minopoly.components.fields.Field;
import de.wolfi.minopoly.components.fields.FieldColor;
import de.wolfi.minopoly.components.fields.FreeParkingField;
import de.wolfi.minopoly.components.fields.FundsField;
import de.wolfi.minopoly.components.fields.JailField;
import de.wolfi.minopoly.components.fields.NormalField;
import de.wolfi.minopoly.components.fields.PayingField;
import de.wolfi.minopoly.components.fields.PoliceField;
import de.wolfi.minopoly.components.fields.StartField;
import de.wolfi.utils.ItemBuilder;
import de.wolfi.utils.inventory.InventoryCounter;
import de.wolfi.utils.inventory.InventorySelector;


public class SetupCommand implements CommandExecutor, Listener {

	/**
	 * Field Setup Items
	 */
	private static final ItemStack field_setup = new ItemBuilder(Material.WOOD_PLATE).setName("§eFields")
			.addLore("< Speichern").build();
	private static final ItemStack field_setup_colorer = new ItemBuilder(Material.INK_SACK).setName("§eColor")
			.addLore("# Gib deiner Straße eine schöne Farbe").build();
	private static final ItemStack field_setup_price = new ItemBuilder(Material.INK_SACK).setName("§ePrice")
			.addLore("# Gib deiner Luxus Villa einen angemessenen Preis").build();
	private static final ItemStack field_setup_size = new ItemBuilder(Material.INK_SACK).setName("§eSize")
			.addLore("# Gib deiner Straße eine prachtvolle Größe").build();
	private static final ItemStack field_setup_renamer = new ItemBuilder(Material.ANVIL).setName("§eName")
			.addLore("> Benenne deine Straße").build();

	private static final ItemStack fieldtype_airportField = new ItemBuilder(Material.MINECART)
			.setName("§7Flughafen Feld").build();
	private static final ItemStack fieldtype_communityField = new ItemBuilder(Material.GOLD_PLATE)
			.setName("§7Community Feld").build();
	private static final ItemStack fieldtype_eventField = new ItemBuilder(Material.IRON_PLATE).setName("§eEvent Feld")
			.build();
	private static final ItemStack fieldtype_fundsField = new ItemBuilder(Material.GOLD_INGOT)
			.setName("§eFundation Feld").build();
	private static final ItemStack fieldtype_freeParkingField = new ItemBuilder(Material.FLOWER_POT_ITEM)
			.setName("§eFreies Parken Feld").build();
	private static final ItemStack fieldtype_payingField = new ItemBuilder(Material.PAPER).setName("§eZahl Feld")
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

	private static final InventoryHolder HOLDER = () -> SetupCommand.minopolyChooser;
	private static final Main MAIN = Main.getMain();
	private static final InventorySelector COLOR_SELECTOR = new InventorySelector("ColorPicker");
	private static final InventoryCounter RANGE_SELECTOR = new InventoryCounter("Range Picker");
	private static final InventoryCounter PRICE_SELECTOR = new InventoryCounter("Price Picker");
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
	private static final Enchantment selected = new ItemBuilder.MyEnchantment("Stupidity");
	/**
	 * Minigame Setup Menu
	 */

	private static final ItemStack minigame_setup = new ItemBuilder(Material.EMERALD).setName("§aMinigame Setup")
			.addLore("< Zurück").build();

	private static final Inventory minopolyChooser;
	private static final HashMap<Player, Minopoly> setups = new HashMap<>();

	// --------------------------------------------

	static {
		minopolyChooser = Bukkit.createInventory(SetupCommand.HOLDER, 9 * 5, "§cWähle eine Welt / Setup");
		for (final World w : Bukkit.getWorlds()) {
			final ItemBuilder i = new ItemBuilder(Material.SPONGE);
			i.setName(w.getName());
			if (!SetupCommand.MAIN.isMinopolyWorld(w))
				i.addLore("Not setup yet!");

			SetupCommand.minopolyChooser.addItem(i.build());
		}

		for (short i = 0; i < 16; i++) {
			COLOR_SELECTOR.addEntry(
					new ItemBuilder(Material.WOOL).setMeta(i).setName(DyeColor.getByWoolData((byte) i).toString()).build());
		}
	}

	public SetupCommand() {
		Bukkit.getPluginManager().registerEvents(this, SetupCommand.MAIN);

	}

	private Inventory createMinigameMainSetup(Minopoly m) {
		final Inventory inv = Bukkit.createInventory(SetupCommand.HOLDER, 9 * 4,
				"§c" + m.getWorldName() + " - Minigames");
		inv.setItem(0, SetupCommand.minigame_main);
		for (MinigameStyleSheet sheet : MinigameRegistry.minigames()) {
			ItemBuilder builder = new ItemBuilder(Material.GOLD_INGOT);
			builder.setName(sheet.getName());
			builder.addLore(sheet.getShortDesc());
			builder.addLore("Players: " + sheet.getMinPlayer() + " - " + sheet.getMaxPlayers());
			builder.addLore(sheet.getUniqIdef().toString());
			if (m.getMinigameManager().hasMinigame(sheet))
				builder.enchant(SetupCommand.selected, 10);
			inv.addItem(builder.build());
		}
		return inv;
	}

	private Inventory createFieldSetup(Minopoly m) {
		final Inventory inv = Bukkit.createInventory(SetupCommand.HOLDER, 9, "§c" + m.getWorldName() + " - Field");
		inv.setItem(0, SetupCommand.field_setup);
		inv.setItem(1, SetupCommand.field_setup_renamer);
		inv.setItem(2, SetupCommand.field_setup_colorer);
		inv.setItem(3, SetupCommand.field_setup_price);
		inv.setItem(4, SetupCommand.field_setup_size);
		return inv;

	}

	private Inventory createGameManagmentInventory(Minopoly m) {
		final Inventory inv = Bukkit.createInventory(SetupCommand.HOLDER, InventoryType.HOPPER,
				"§c" + m.getWorldName() + " - Setup");
		inv.setItem(0, SetupCommand.main_setup);
		inv.setItem(1, SetupCommand.main_setup_fieldItem);
		inv.setItem(2, SetupCommand.main_setup_minigameItem);

		return inv;
	}

	private void giveFieldSetupItems(HumanEntity whoClicked) {
		whoClicked.getInventory().addItem(SetupCommand.fieldtype_normalField, SetupCommand.fieldtype_eventField,
				SetupCommand.fieldtype_communityField, SetupCommand.fieldtype_policeField,
				SetupCommand.fieldtype_jailField, SetupCommand.fieldtype_startField,
				SetupCommand.fieldtype_freeParkingField, SetupCommand.fieldtype_airportField,
				SetupCommand.fieldtype_fundsField, SetupCommand.fieldtype_payingField);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		final ItemStack clicked = e.getCurrentItem();
		if (clicked == null)
			return;
		if (e.getClickedInventory().getHolder() != SetupCommand.HOLDER)
			return;
		e.setCancelled(true);
		if (e.getClickedInventory().equals(SetupCommand.minopolyChooser)) {

			final World w = Bukkit.getWorld(clicked.getItemMeta().getDisplayName());
			if (w == null) {
				Bukkit.broadcastMessage("§cError while converting world");
				return;
			}
			final Minopoly m = SetupCommand.MAIN.loadMap(w);
			SetupCommand.setups.put((Player) e.getWhoClicked(), m);
			e.getWhoClicked().openInventory(this.createGameManagmentInventory(m));
		} else {
			final Minopoly m = SetupCommand.setups.get(e.getWhoClicked());
			final ItemStack checker = e.getClickedInventory().getItem(0);
			if (checker.equals(SetupCommand.main_setup)) {
				if (clicked.equals(SetupCommand.main_setup)) {
					e.getWhoClicked().openInventory(SetupCommand.minopolyChooser);
				}
				if (clicked.equals(SetupCommand.main_setup_fieldItem))
					this.giveFieldSetupItems(e.getWhoClicked());
				if (clicked.equals(SetupCommand.main_setup_minigameItem))
					e.getWhoClicked().openInventory(this.createMinigameMainSetup(m));
			} else if (checker.equals(SetupCommand.field_setup)) {
				if (clicked.equals(SetupCommand.field_setup_renamer)) {
					AnvilGUI gui = new AnvilGUI((Player) e.getWhoClicked(), (event) -> {
						e.setCurrentItem(new ItemBuilder(Material.PAPER).setName(event.getName()).build());
						event.setWillClose(false);
						e.getWhoClicked().openInventory(e.getInventory());
					});
					gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, field_setup_renamer);
					gui.open("RENAME YOUR STREET");
				} else if (clicked.equals(SetupCommand.field_setup_colorer)) {
					COLOR_SELECTOR.setCallback((i) -> {
						e.setCurrentItem(i);
						e.getWhoClicked().openInventory(e.getInventory());
						return true;
					});
					COLOR_SELECTOR.open((Player) e.getWhoClicked());
				} else if (clicked.equals(SetupCommand.field_setup_size)) {
					RANGE_SELECTOR.setCallback((i) -> {
						e.setCurrentItem(i);
						e.getWhoClicked().openInventory(e.getInventory());
						return true;
					});
					RANGE_SELECTOR.open((Player) e.getWhoClicked());
				} else if (clicked.equals(SetupCommand.field_setup_price)) {
					PRICE_SELECTOR.setCallback((i) -> {
						e.setCurrentItem(i);
						e.getWhoClicked().openInventory(e.getInventory());
						return true;
					});
					PRICE_SELECTOR.open((Player) e.getWhoClicked());
				} else if (clicked.equals(SetupCommand.field_setup)) {
					if (!(e.getClickedInventory().contains(Material.INK_SACK)
							&& e.getClickedInventory().contains(Material.ANVIL))) {
						Field f = m.getFieldManager().addField(new NormalField(
								e.getClickedInventory().getItem(1).getItemMeta().getDisplayName(),
								FieldColor.getByColor(DyeColor
										.valueOf(e.getClickedInventory().getItem(2).getItemMeta().getDisplayName())),
								e.getWhoClicked().getLocation(), m, e.getClickedInventory().getItem(4).getAmount(),
								e.getClickedInventory().getItem(3).getAmount()));
						e.getWhoClicked().closeInventory();
						e.getWhoClicked().sendMessage("5 Sekudnen!!!");
						Bukkit.getScheduler().runTaskLater(MAIN, () -> f.setHome(e.getWhoClicked().getLocation()),
								20 * 5);
					}
				}
			} else if (checker.equals(SetupCommand.minigame_main)) {
				if (clicked.equals(checker)) {
					e.getWhoClicked().openInventory(this.createGameManagmentInventory(m));
				} else {
					MinigameStyleSheet sheet = MinigameRegistry
							.loadStyleFromUUID(UUID.fromString(clicked.getItemMeta().getLore().get(2)));
					boolean enabled = checker.containsEnchantment(SetupCommand.selected);
					if (enabled) {
						m.getMinigameManager().removeMinigame(sheet);
						clicked.removeEnchantment(SetupCommand.selected);
						((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
					} else {
						m.getMinigameManager().addMinigame(m, sheet);
						clicked.addUnsafeEnchantment(SetupCommand.selected, 10);
						((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
					}

				}
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
			final Minopoly mo = SetupCommand.setups.get(e.getPlayer());
			if (mo == null)
				return;
			final FieldManager m = mo.getFieldManager();
			if (e.getItem().equals(SetupCommand.fieldtype_normalField)) {
				e.getClickedBlock().setType(Material.AIR);
				e.getPlayer().teleport(e.getClickedBlock().getLocation());
				e.getPlayer().openInventory(this.createFieldSetup(mo));
			} else if (e.getItem().equals(SetupCommand.fieldtype_eventField)) {
				RANGE_SELECTOR.setCallback((i) -> {
					m.addField(new EventField(e.getClickedBlock().getLocation(), mo, i.getAmount()));
					return true;
				});
				RANGE_SELECTOR.open(e.getPlayer());
			} else if (e.getItem().equals(SetupCommand.fieldtype_communityField)) {
				RANGE_SELECTOR.setCallback((i) -> {
					m.addField(new CommunityField(e.getClickedBlock().getLocation(), mo, i.getAmount()));
					return true;
				});
				RANGE_SELECTOR.open(e.getPlayer());
			} else if (e.getItem().equals(SetupCommand.fieldtype_startField)) {
				RANGE_SELECTOR.setCallback((i) -> {
					m.addField(new StartField(e.getClickedBlock().getLocation(), mo, i.getAmount()));
					return true;
				});
				RANGE_SELECTOR.open(e.getPlayer());
			} else if (e.getItem().equals(SetupCommand.fieldtype_policeField)) {
				RANGE_SELECTOR.setCallback((i) -> {
					m.addField(new PoliceField(e.getClickedBlock().getLocation(), mo, i.getAmount()));
					return true;
				});
				RANGE_SELECTOR.open(e.getPlayer());
			} else if (e.getItem().equals(SetupCommand.fieldtype_jailField)) {
				RANGE_SELECTOR.setCallback((i) -> {
					m.addField(new JailField(e.getClickedBlock().getLocation(), mo, i.getAmount()));
					return true;
				});
				RANGE_SELECTOR.open(e.getPlayer());
			} else if (e.getItem().equals(SetupCommand.fieldtype_freeParkingField)) {
				RANGE_SELECTOR.setCallback((i) -> {
					m.addField(new FreeParkingField(e.getClickedBlock().getLocation(), mo, i.getAmount()));
					return true;
				});
				RANGE_SELECTOR.open(e.getPlayer());
			} else if (e.getItem().equals(SetupCommand.fieldtype_airportField)) {
				AnvilGUI gui = new AnvilGUI(e.getPlayer(), (event) -> {
					event.setWillClose(false);
					PRICE_SELECTOR.setCallback((ip) -> {
						RANGE_SELECTOR.setCallback((ir) -> {

							m.addField(new AirportField(event.getName(), e.getClickedBlock().getLocation(), mo,
									ir.getAmount(), ip.getAmount()));
							return true;
						});
						RANGE_SELECTOR.open(e.getPlayer());

						return true;
					});
					PRICE_SELECTOR.open(e.getPlayer());
				});
				gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, field_setup_renamer);
				gui.open("RENAME YOUR STREET");
			} else if (e.getItem().equals(SetupCommand.fieldtype_fundsField)) {
				AnvilGUI gui = new AnvilGUI(e.getPlayer(), (event) -> {
					event.setWillClose(false);
					PRICE_SELECTOR.setCallback((ip) -> {

						RANGE_SELECTOR.setCallback((ir) -> {

							m.addField(new FundsField(event.getName(), e.getClickedBlock().getLocation(), mo,
									ir.getAmount(), ip.getAmount()));
							return true;
						});
						RANGE_SELECTOR.open(e.getPlayer());
						return true;

					});
					PRICE_SELECTOR.open(e.getPlayer());

				});
				gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, field_setup_renamer);
				gui.open("RENAME YOUR STREET");
			} else if (e.getItem().equals(SetupCommand.fieldtype_payingField)) {
				AnvilGUI gui = new AnvilGUI(e.getPlayer(), (event) -> {
					event.setWillClose(false);

					RANGE_SELECTOR.setCallback((ir) -> {

						m.addField(new PayingField(event.getName(), e.getClickedBlock().getLocation(), mo,
								ir.getAmount()));
						return true;
					});
					RANGE_SELECTOR.open(e.getPlayer());

				});
				gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, field_setup_renamer);
				gui.open("RENAME YOUR STREET");
			}
		}
	}

}
