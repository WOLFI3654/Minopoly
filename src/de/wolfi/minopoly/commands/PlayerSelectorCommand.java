package de.wolfi.minopoly.commands;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.DummyPlayer;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.components.SerializeablePlayer;
import de.wolfi.minopoly.utils.FigureType;
import de.wolfi.minopoly.utils.Messages;
import de.wolfi.utils.ItemBuilder;

public class PlayerSelectorCommand extends CommandInterface implements InventoryHolder{

	
	
	
	public PlayerSelectorCommand(Main plugin) {
		super(plugin, 0, true,true);
	}

	@Override
	protected void executeCommand(Minopoly board, Player player, String[] args) {
		player.getHook().openInventory(this.getSelector(board));
	}
	
	
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getClickedInventory().getHolder() == this){
			e.setCancelled(true);
			if(e.getCurrentItem() == null) return;
			DummyPlayer player = this.getDummyPlayer(e.getWhoClicked().getName());
			FigureType t = FigureType.valueOf(e.getCurrentItem().getItemMeta().getLore().get(4));
			SerializeablePlayer sp = player.getBoard().getBySPFigureType(t);
			if(sp.isSelected())
				Messages.FIGURE_ALREADY_TAKEN.send(e.getWhoClicked(),t.getDisplay());
				else {
					player.getBoard().selectPlayer((org.bukkit.entity.Player) e.getWhoClicked(), t);
					e.getWhoClicked().closeInventory();
					((org.bukkit.entity.Player)e.getWhoClicked()).setResourcePack("https://github.com/WOLFI3654/Minopoly-RessourcePack/releases/download/0.0/Minopoly-RessourcePack-master.zip");
				}
		}
	}
	
	
	
	
	
	private Inventory getSelector(Minopoly board){
		Inventory inv = Bukkit.createInventory(this, 9*3,"§6Select Figure");
		for(SerializeablePlayer p : board.getFigures()){
			inv.addItem(this.createEntry(p));
		}
		return inv;
	}
	
	private String availableStatus(SerializeablePlayer player){
		StringBuilder s = new StringBuilder();
		s.append(!player.isSelected());
		if(player.isSelected()){
			s.append('(');
			s.append(player.getPlayer().getName());
			s.append(')');
		}
		return s.toString();
	}

	private ItemStack createEntry(SerializeablePlayer p) {
		ItemBuilder i = ItemBuilder.copyFromItemstack(p.getF().getItem());
		i.setName(p.isSelected()?"§c":"§a"+p.getF().getDisplay());
		i.addLore("§7Type: "+p.getF().getName());
		i.addLore("§7Field: "+p.getLoc());
		i.addLore("§7Money: "+p.getBoard().getBank().getMoney(p.getBankCard()));
		i.addLore("§7Available: "+availableStatus(p));
		i.addLore(String.valueOf(p.getF().name()));
		return i.build();
	}

	@Override
	public Inventory getInventory() {
		Bukkit.broadcastMessage("Meet me");
		return null;
	}
	
	

}
