package de.wolfi.minopoly.commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.MinigameRegistry;
import de.wolfi.minopoly.MinigameRegistry.MinigameStyleSheet;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.components.minigames.Minigame;
import de.wolfi.minopoly.utils.TeleportCause;
import de.wolfi.utils.ItemBuilder;

public class MinigameCommand extends CommandInterface {
	private static final InventoryHolder HOLDER = () -> MinigameCommand.minigameSelector;

	private static final Inventory minigameSelector;

	private static final ItemStack seperator = new ItemBuilder(Material.STAINED_GLASS_PANE).setMeta((short) 9).build();

	private final HashMap<org.bukkit.entity.Player, Minopoly> boards = new HashMap<>();

	static {
		minigameSelector = Bukkit.createInventory(MinigameCommand.HOLDER, 9 * 3, "§cWähle ein Minispiel!");
		for (int i = 0; i < 9 * 3; i++)
			minigameSelector.setItem(i, MinigameCommand.seperator);
	}

	public MinigameCommand(Main plugin) {
		super(plugin, 1, true);
	}

	@Override
	protected void executeCommand(Minopoly board, Player player, String[] args) {
		switch (args[0]) {
		case "select":
			calculateMinigames(board);
			this.boards.put(player.getHook(), board);
			player.getHook().openInventory(MinigameCommand.minigameSelector);
			break;
		case "start":
			board.getMinigameManager().getCurrentGame().getMinigameHook().start();
			break;
		case "stop":
			HandlerList.unregisterAll(board.getMinigameManager().getCurrentGame().getMinigameHook());
			board.getMinigameManager().getCurrentGame().getMinigameHook().endGame();
			for(Player p : board.getPlayingPlayers()){
				p.teleport(p.getLocation().getTeleportLocation(), TeleportCause.MINIGAME_END);
			}
			break;
		default:
			break;
		}

	}

	private void calculateMinigames(Minopoly board) {
		for (int i = 9 * 1 + 2; i < 9 * 2-1; i += 2) {
			MinigameStyleSheet style = board.getMinigameManager().randomMinigame().getStyle();
			minigameSelector.setItem(i,
					new ItemBuilder(Material.ITEM_FRAME).setName(style.getName()).addLore(style.getShortDesc())
							.addLore(style.getMinPlayer() + " - " + style.getMaxPlayers())
							.addLore(style.getUniqIdef().toString()).build());
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getClickedInventory().getHolder() != MinigameCommand.HOLDER)
			return;
		if (MinigameCommand.seperator.equals(e.getCurrentItem()))
			return;
		if (e.getCurrentItem().getType() == Material.ITEM_FRAME) {
			Minigame minigame = boards.get(e.getWhoClicked()).getMinigameManager().getMinigameOrNull(
					MinigameRegistry.loadStyleFromUUID(UUID.fromString(e.getCurrentItem().getItemMeta().getLore().get(2))));
			this.boards.get(e.getWhoClicked()).getMinigameManager().playMinigame(this.boards.get(e.getWhoClicked()), minigame);
			Bukkit.getPluginManager().registerEvents(minigame.getMinigameHook(), Main.getMain());
			
		}
	}

}
