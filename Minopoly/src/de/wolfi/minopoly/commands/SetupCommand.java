package de.wolfi.minopoly.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;

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
		Inventory inv = Bukkit.createInventory(holder, 9*5,"§c"+m.getWorldName()+" - Setup");
		inv.setItem(0, mainSetup);
		
		
		return inv;
	}
	
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getCurrentItem() == null) return;
		if(e.getClickedInventory().getHolder() != holder) return;
		if(e.getClickedInventory() == minopolyChooser){
			ItemStack clicked = e.getCurrentItem();
			World w = Bukkit.getWorld(clicked.getItemMeta().getDisplayName());
			if(w == null) Bukkit.broadcastMessage("§cError while converting world");
			Minopoly m = main.loadMap(w);
			setups.put((Player) e.getWhoClicked(), m);
			e.getWhoClicked().openInventory(createGameManagmentInventory(m));
		}else{
			ItemStack checker = e.getClickedInventory().getItem(0);
			if(checker.equals(mainSetup)){
				
			}else if(checker.equals(mainMinigameSetup)){
				
			}else if(checker.equals(minigameSetup)){
				
			}else e.getWhoClicked().sendMessage("§cError while converting inventory");
		}
	}

}
