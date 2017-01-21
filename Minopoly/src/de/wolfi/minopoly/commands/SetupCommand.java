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
import de.wolfi.minopoly.components.fields.EventField;
import de.wolfi.utils.ItemBuilder;

public class SetupCommand implements CommandExecutor,Listener {

	private static final Inventory minopolyChooser;
	private static final Main main = Main.getMain();
	
	private static final HashMap<Player,Minopoly> setups = new HashMap<>();
	
	private static final InventoryHolder holder = () -> SetupCommand.minopolyChooser;	
	
	//-------------------------------------------
	/**
	 * Main Setup Menu
	 */
	private static final ItemStack mainSetup = new ItemBuilder(Material.SAPLING).setName("§aMain Setup").addLore("< Zurück").build();
	private static final ItemStack fieldSetupItem = new ItemBuilder(Material.WOOD_PLATE).setName("§eFields").addLore(" Füge Felder hinzu").build();
	private static final ItemStack mainMinigameSetupItem = new ItemBuilder(Material.EMERALD).setName("§eMinigames").addLore("> Minigame Verwaltung").build();
	
	//-------------------------------------------
	/**
	 * Main Minigame Menu
	 */
	
	private static final ItemStack mainMinigameSetup = new ItemBuilder(Material.CHEST).setName("§aMinigame übersicht").addLore("< Zurück").build();
	
	
	//--------------------------------------------
	
	/**
	 * Minigame Setup Menu
	 */
	
	private static final ItemStack minigameSetup = new ItemBuilder(Material.EMERALD).setName("§aMinigame Setup").addLore("< Zurück").build();
	
	//--------------------------------------------
	
	/**
	 * Field Setup Items
	 */
	
	private static final ItemStack normalField = new ItemBuilder(Material.WOOD_PLATE).setName("§7Normales Feld").build();
	private static final ItemStack eventField = new ItemBuilder(Material.IRON_PLATE).setName("§Event Feld").build();
	private static final ItemStack communityField = new ItemBuilder(Material.GOLD_PLATE).setName("§7Community Feld").build();
	private static final ItemStack policeField = new ItemBuilder(Material.IRON_SWORD).setName("§7Polizei Feld").build();
	private static final ItemStack jailField = new ItemBuilder(Material.IRON_BARDING).setName("§7Jelly Feld").build();
	private static final ItemStack startField = new ItemBuilder(Material.BANNER).setName("§7Start Feld").build();
	
	
	//--------------------------------------------
	
	static{
		minopolyChooser = Bukkit.createInventory(holder, 9*5,"§cWähle eine Welt / Setup");
		for(World w : Bukkit.getWorlds()){
			ItemBuilder i = new ItemBuilder(Material.SPONGE);
			i.setName(w.getName());
			if(!main.isMinopolyWorld(w)){
				i.addLore("Not setup yet!");
			}
			
			minopolyChooser.addItem(i.build());
		}
	}
	
	
	
	public SetupCommand() {
		Bukkit.getPluginManager().registerEvents(this, main);
		
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player){
			((Player) arg0).openInventory(minopolyChooser);
		}
		return true;
	}
	
	private Inventory createGameManagmentInventory(Minopoly m){
		Inventory inv = Bukkit.createInventory(holder, InventoryType.HOPPER,"§c"+m.getWorldName()+" - Setup");
		inv.setItem(0, mainSetup);
		inv.setItem(1, fieldSetupItem);
		inv.setItem(2, mainMinigameSetupItem);
		
		return inv;
	}
	
	private Inventory createFieldSetup(){
		return null;
		
	}
	
	private void giveFieldSetupItems(HumanEntity whoClicked) {
		whoClicked.getInventory().addItem(normalField,eventField,communityField,policeField,jailField,startField);		
	}

	
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		final ItemStack clicked = e.getCurrentItem();
		if(clicked == null) return;
		if(e.getClickedInventory().getHolder() != holder) return;
		
		if(e.getClickedInventory() == minopolyChooser){
			
			World w = Bukkit.getWorld(clicked.getItemMeta().getDisplayName());
			if(w == null) Bukkit.broadcastMessage("§cError while converting world");
			Minopoly m = main.loadMap(w);
			setups.put((Player) e.getWhoClicked(), m);
			e.getWhoClicked().openInventory(createGameManagmentInventory(m));
		}else{
			ItemStack checker = e.getClickedInventory().getItem(0);
			if(checker.equals(mainSetup)){
				if(clicked.equals(fieldSetupItem)) giveFieldSetupItems(e.getWhoClicked());
			}else if(checker.equals(mainMinigameSetup)){
				
			}else if(checker.equals(minigameSetup)){
				
			}else e.getWhoClicked().sendMessage("§cError while converting inventory");
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(e.getItem() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK){
			Minopoly m = setups.get(e.getPlayer());
			if(e.getItem().equals(normalField)){
				
			}else if(e.getItem().equals(eventField)){
				m.addField(new EventField(e.getClickedBlock().getLocation(), m));
			}
		}
	}
	

	
}
