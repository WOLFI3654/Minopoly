package de.wolfi.minopoly.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.utils.ItemBuilder;

public class EventCommand extends CommandInterface {
	
	public static enum Events{
		LOTTERY("Du gewinnst in der Lotterie 50 Katzenbabys","bank %s add 50 Lotterie"),
		LOTTERY1("Du gewinnst in der Lotterie 150 Katzenbabys","bank %s add 150 Lotterie"),
		LOTTERY2("Du gewinnst in der Lotterie 250 Katzenbabys","bank %s add 250 Lotterie"),
		LOTTERY3("Du gewinnst in der Lotterie 350 Katzenbabys","bank %s add 350 Lotterie");
		
		private String name;
		private String cmd;

		private Events(String name, String cmd) {
			this.name =name;
			this.cmd = cmd;
		}
		
		public String getCmd() {
			return cmd;
		}
		
		public String getName() {
			return name;
		}
	}
	
	private static final InventoryHolder HOLDER = () -> EventCommand.eventSelector;

	private static final Inventory eventSelector;

	private static final ItemStack seperator = new ItemBuilder(Material.STAINED_GLASS_PANE).setMeta((short) 9).build();

	private final HashMap<org.bukkit.entity.Player, Minopoly> boards = new HashMap<>();

	static {
		eventSelector = Bukkit.createInventory(EventCommand.HOLDER, 9 * 3, "§cWähle eine Karte!");
		for (int i = 0; i < 9 * 3; i++)
			eventSelector.setItem(i, EventCommand.seperator);
	}

	public EventCommand(Main plugin) {
		super(plugin, 1, true);
	}

	@Override
	protected void executeCommand(Minopoly board, Player player, String[] args) {
		switch (args[0]) {
		case "select":
			calculateEvents(board);
			this.boards.put(player.getHook(), board);
			player.getHook().openInventory(EventCommand.eventSelector);
			break;
		default:
			break;
		}

	}

	private void calculateEvents(Minopoly board) {
		for (int i = 0; i < 9 * 3; i++){
			Events event = Events.values()[(int) (Math.random()*Events.values().length)];
			eventSelector.setItem(i,
					new ItemBuilder(Material.ITEM_FRAME).setName("§r"+event.toString()).build());
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getClickedInventory().getHolder() != EventCommand.HOLDER)
			return;
		if (EventCommand.seperator.equals(e.getCurrentItem()))
			return;
		if (e.getCurrentItem().getType() == Material.ITEM_FRAME) {
			///XXX
			Events ve = Events.valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(ve.getCmd(),e.getWhoClicked().getName()));
			Bukkit.broadcastMessage(ve.getName());
			e.getWhoClicked().closeInventory();
			
		}
	}

}
