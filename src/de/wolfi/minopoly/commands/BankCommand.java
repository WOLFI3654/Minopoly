package de.wolfi.minopoly.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Messages;
import de.wolfi.utils.ItemBuilder;
import de.wolfi.utils.inventory.InventoryCounter;

public class BankCommand extends CommandInterface implements InventoryHolder {

	public static final ItemStack payGUI = new ItemBuilder(Material.PAPER).setName("§aTransaktionen tätigen").build();
	
	private final String title = "Transaktionen tätigen";

	private final ItemStack pottStack = new ItemBuilder(Material.FLOWER_POT_ITEM).setName("§bPOTT").build();

	public BankCommand(Main plugin) {
		super(plugin, 3, true);
	}

	// bank pay *USER* *AMOUNT* *REASON*
	// bank get *USER* *AMOUNT* *REASON*
	// bank move *FROM* *TO* *AMOUNT* *REASON*
	@Override
	public boolean onCommand(final CommandSender paramCommandSender, final Command paramCommand,
			final String paramString, final String[] args) {
		if (paramCommandSender instanceof ConsoleCommandSender)
			return false;
		final org.bukkit.entity.Player sender = (org.bukkit.entity.Player) paramCommandSender;
		if (!Main.getMain().isMinopolyWorld(sender.getWorld())) {
			sender.sendMessage("command.wrongworld");
			return true;
		}
		final Minopoly game = Main.getMain().getMinopoly(sender.getWorld());
		if (args.length < 4) {
			paramCommandSender.sendMessage("args.missing");
			return true;
		}
		final org.bukkit.entity.Player playername = Bukkit.getPlayer(args[1]);
		if (playername == null) {
			sender.sendMessage("player.missing");
			return true;
		}
		final Player p = game.getByBukkitPlayer(playername);
		if (p == null) {
			sender.sendMessage("player.missing.game");
			return true;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(final CommandSender paramCommandSender, final Command paramCommand,
			final String paramString, final String[] paramArrayOfString) {
		// TODO
		return null;
	}

	@Override
	protected void executeCommand(Minopoly board, Player player, String[] args) {
		final int amount = Integer.parseInt(args[1]);
		final String money = args[1];
		final String reason = args[2];
		switch (args[0]) {
		case "pay": {

			player.addMoney(amount, reason);
			Messages.MONEY_GLOBAL_PAID.send(board, money, player.getDisplay());
		}
			break;
		case "get": {
			player.removeMoney(amount, reason);
			Messages.MONEY_GLOBAL_GOT.send(board, money, player.getDisplay(), reason);
		}
			break;
		case "move": {
			if (args.length < this.getMinArgs() + 1) {
				Messages.COMMAND_NO_ARGUMENTS.send(board);
				return;
			}
			final Player pr = board.getByPlayerName(args[3]);
			if (pr == null) {
				Messages.COMMAND_NO_PLAYER.send(board, args[3]);
				return;
			}
			player.transferMoneyTo(pr, amount, reason);
			Messages.MONEY_GLOBAL_TRANSFER.send(board, money, player.getDisplay(), pr.getDisplay(), reason);
		}
			break;
		case "gui": {
			Inventory inv = Bukkit.createInventory(this, 9 * 2, title);
			for (Player p : board.getPlayingPlayers()) {
				if (p != player)
					inv.addItem(new ItemBuilder(Material.SKULL_ITEM).setMeta((short) 3).setSkullOwner(p.getName())
							.setName(p.getDisplay()).build());
			}
			inv.setItem(9 * 2 - 1, pottStack);
			player.getHook().openInventory(inv);
		}

		default:
			break;
		}
	}
	

	@EventHandler
	public void onItemUse(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR
				|| e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem() != null) {
			if (e.getItem().equals(payGUI)) {
				e.setCancelled(true);
				Bukkit.dispatchCommand(Main.getMain().getMinopoly(e.getPlayer().getWorld()),"bank "+e.getPlayer().getName()+ " gui");
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getInventory().getHolder() == this){
			e.setCancelled(true);
			if(e.getCurrentItem() != null){
				if(e.getCurrentItem().getType() == Material.FLOWER_POT_ITEM || e.getCurrentItem().getType() == Material.SKULL_ITEM){
					Minopoly game = Main.getMain().getMinopoly(e.getWhoClicked().getWorld());
					InventoryCounter counter = new InventoryCounter("Wie viel soll "+e.getCurrentItem().getItemMeta().getDisplayName()+" bekommen?");
					counter.setCallback((c)->{
						int amount = c.getAmount();
						
						if(e.getCurrentItem().getType() == Material.SKULL_ITEM)
							game.getByBukkitPlayer((org.bukkit.entity.Player) e.getWhoClicked()).transferMoneyTo(game.getByPlayerName(((SkullMeta) e.getCurrentItem().getItemMeta()).getOwner()),amount, "DirektPay");
						else if(e.getCurrentItem().getType() == Material.FLOWER_POT_ITEM){
							game.getByBukkitPlayer((org.bukkit.entity.Player) e.getWhoClicked()).removeMoney(amount,"DirektPay: POTT");
							game.getTHE_POTT_OF_DOOM___andmore_cute_puppies().addMoney(amount);
						}
						counter.destroy();
						return true;
					});
					counter.open((org.bukkit.entity.Player) e.getWhoClicked());
				}
			}
		}
	}

	@Override
	public Inventory getInventory() {
		return Bukkit.createInventory(this, 9, title);
	}

}
