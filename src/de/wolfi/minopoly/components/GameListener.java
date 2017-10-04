package de.wolfi.minopoly.components;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.events.DiceEvent;
import de.wolfi.minopoly.events.FieldEvent;
import de.wolfi.minopoly.events.MinigameFoundEvent;
import de.wolfi.minopoly.events.MinigameWinEvent;
import de.wolfi.minopoly.events.MoneyEvent;
import de.wolfi.minopoly.events.MoveFinishedEvent;
import de.wolfi.minopoly.events.NextPlayerEvent;
import de.wolfi.minopoly.events.PlayerJailedEvent;
import de.wolfi.minopoly.utils.CancelConstants;
import de.wolfi.minopoly.utils.Messages;
import de.wolfi.utils.ItemBuilder;

public class GameListener implements Listener {


	public static final ItemStack finishMove = new ItemBuilder(Material.SKULL_ITEM).setMeta((short)3).setSkullOwner("MHF_ARROW_"
			+ ""
			+ "LEFT").setName("§aZug beenden").build();
	
	private byte internalCounter = 0;
	private Minopoly game;
	private Player currentPlayer;
	public GameListener(Minopoly game) {
		this.game = game;
		Bukkit.getPluginManager().registerEvents(this, Main.getMain());
	}
	
	
	@EventHandler
	public void onItemUse(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem() != null){
			if(e.getItem().equals(finishMove) && e.getPlayer().equals(currentPlayer.getHook())){
				Bukkit.getPluginManager().callEvent(new MoveFinishedEvent(currentPlayer));
			}
		}
	}
	@EventHandler
	public void onDice(DiceEvent e){
		if(this.isAuto()){
			if(currentPlayer.isJailed()){
				if(internalCounter >= 3){
					Bukkit.getPluginManager().callEvent(new NextPlayerEvent());
				}else
				if(e.isPasch()){
					this.game.unjailPlayer(e.getPlayer().getFigure());
					Bukkit.dispatchCommand(this.game,"dice "+e.getPlayer().getName());
					Messages.JAIL_EXIT.broadcast(e.getPlayer().getDisplay());
				}else {
					Bukkit.dispatchCommand(this.game,"dice "+e.getPlayer().getName());
					internalCounter++;
					Messages.JAIL_EXIT_FAILED.send(e.getPlayer().getHook(), 3-internalCounter);
					Bukkit.getPluginManager().callEvent(new NextPlayerEvent());

				}
				
			return;
			}
			e.getPlayer().move(e.getOne()+e.getTwo());
			if(e.isPasch()){
				Bukkit.broadcastMessage("Pasch");
				internalCounter++;
				if(internalCounter >=3){
					Bukkit.getPluginManager().callEvent(new PlayerJailedEvent(e.getPlayer()));
					Messages.TRIPPLE_JAILED.broadcast(e.getPlayer().getDisplay());
					
				}else return;
			}
		}
	}
	
	@EventHandler
	public void onMoney(MoneyEvent e){
		game.getScoreboardManager().updatePlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onMinigameWin(MinigameWinEvent e){
		if(this.isAuto()){
			Bukkit.dispatchCommand(this.game, "minigame "+e.getPlayer().getName()+ " stop");
		}
	}
	
	@EventHandler
	public void onMinigameFound(MinigameFoundEvent e){
		if(this.isAuto()){
			Bukkit.dispatchCommand(this.game, "minigame "+e.getPlayer().getName()+ " select");
		}
	}
	
	@EventHandler
	public void onPlayerJailed(PlayerJailedEvent e){
		if(this.isAuto()){
			e.getPlayer().teleport(this.game.getFieldManager().getJailField());
			this.game.jailPlayer(e.getPlayer().getFigure());
		}
	}
	
	@EventHandler
	public void onMoveFinished(MoveFinishedEvent e){
		if(internalCounter > 0){
			Bukkit.dispatchCommand(this.game,"dice "+e.getPlayer().getName());
			return;
		}
		Bukkit.getPluginManager().callEvent(new NextPlayerEvent());
		Messages.MOVE_FINISHED.broadcast(e.getPlayer().getDisplay());


	}
	
	@EventHandler
	public void onFieldEvent(FieldEvent e){
		if(this.isAuto()){
			if(e.getField().isOwned() && !e.getField().isOwnedBy(e.getPlayer())){
				e.getPlayer().transferMoneyTo(e.getField().getOwner(), e.getField().getBilling(), "Schulden :3");
			}
		}
	}
	
	@EventHandler
	public void onNext(NextPlayerEvent e){
		if(this.isAuto()){
			internalCounter = 0;
			currentPlayer = getNext();
			Bukkit.dispatchCommand(game, "dice "+currentPlayer.getName());

		}
	}
	
	private Player getNext() {
		if(currentPlayer == null) return game.getPlayingPlayers().get(0);
		boolean next = false;
		for (final Player p : game.getPlayingPlayers()) {
			if (p.equals(currentPlayer)) {
				next = true;
				continue;
			}
			if (next)
				return p;
		}

		return game.getPlayingPlayers().get(0);
	}

	private boolean isAuto(){
		return this.game.getSettings().isAuto();
	}
	
	
	@EventHandler
	public void onDamageNPC(EntityDamageEvent e){
		if(e.getEntity().hasMetadata("PlayerNPC") || e.getEntity().hasMetadata(CancelConstants.CANCEL_DAMAGE)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onChangeWorld(EntityChangeBlockEvent e){
		if(e.getEntity().hasMetadata(CancelConstants.CANCEL_BLOCK_CHANGE)){
			e.setCancelled(true);
		}
	}
}
