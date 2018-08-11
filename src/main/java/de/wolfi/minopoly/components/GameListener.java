package de.wolfi.minopoly.components;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.fields.FieldColor;
import de.wolfi.minopoly.components.fields.PayingField;
import de.wolfi.minopoly.events.*;
import de.wolfi.minopoly.utils.CancelConstants;
import de.wolfi.minopoly.utils.I18nHelper;
import de.wolfi.minopoly.utils.TeleportCause;
import de.wolfi.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GameListener implements Listener {

    public static final ItemStack finishMove = new ItemBuilder(Material.SKULL_ITEM).setMeta((short) 3)
            .setSkullOwner("MHF_ArrowLeft").setName("§aZug beenden").build();

    private byte internalCounter = 0;
    private byte lastDice = 0;
    private Minopoly game;
    private Player currentPlayer;

    public GameListener(Minopoly game) {
        this.game = game;
        Bukkit.getPluginManager().registerEvents(this, Main.getMain());
//		Bukkit.getScheduler().runTaskTimer(Main.getMain(), () -> ActionBarAPI
//				.sendActionBarToAllPlayers(currentPlayer.getName() + " | " + lastDice + " | " + internalCounter), 60,
//				62);
    }


    @EventHandler
    public void onItemUse(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR
                || e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem() != null) {
            if (e.getItem().getType() == Material.SKULL_ITEM) {
//				TitlesAPI.sendTabTitle(e.getPlayer(),finishMove.toString(), e.getItem().toString());
                if (ItemBuilder.isSimilar(GameListener.finishMove, e.getItem())) {
                    if (e.getPlayer().getUniqueId().equals(currentPlayer.getHook().getUniqueId())) {
                        Bukkit.getPluginManager().callEvent(new MoveFinishedEvent(currentPlayer));
                    } else///XXX ferdinand.minopoly.not_your_turn
                        e.getPlayer().sendMessage("Du nix dran...");
                }
            }
        }
    }

    @EventHandler
    public void onDice(DiceEvent e) {
        this.lastDice = (byte) ((byte) e.getOne() + e.getTwo());
        if (this.isAuto()) {
            if (currentPlayer.isJailed()) {
                if (internalCounter >= 3) {
                    Bukkit.getPluginManager().callEvent(new NextPlayerEvent());
//					Messages.MOVE_FINISHED.broadcast(e.getPlayer().getDisplay());
                    I18nHelper.broadcast("minopoly.gameplay.move_finished", false, e.getPlayer().getDisplay());
                } else if (e.isPasch()) {
                    this.game.unjailPlayer(e.getPlayer().getFigure());
                    this.internalCounter = 0;
                    Bukkit.dispatchCommand(this.game, "dice " + e.getPlayer().getName());
                    //Messages.JAIL_EXIT.broadcast(e.getPlayer().getDisplay());
                    I18nHelper.broadcast("minopoly.ferdinand.jail_exit", true, e.getPlayer().getDisplay());

                } else {
                    Bukkit.dispatchCommand(this.game, "dice " + e.getPlayer().getName());
                    internalCounter++;
                    e.getPlayer().sendMessage("minopoly.gameplay.jail_exit_failed", false, String.valueOf(3 - (internalCounter - 1)));
//					Messages.JAIL_EXIT_FAILED.send(e.getPlayer().getHook(), 3 - internalCounter);

                }

                return;
            }
            e.getPlayer().move(e.getOne() + e.getTwo());
            if (e.isPasch()) {
                internalCounter++;
                if (internalCounter >= 3) {
                    Bukkit.getPluginManager().callEvent(new PlayerJailedEvent(e.getPlayer()));
                    //Messages.TRIPPLE_JAILED.broadcast(e.getPlayer().getDisplay());
                    I18nHelper.broadcast("minopoly.ferdinand.jail_tripple_pasch", true);
                    e.getPlayer().sendMessage("minopoly.gameplay.jail_tripple_pasch", false);
//					

                } else
                    return;
            } else internalCounter = 0;
        }
    }

    @EventHandler
    public void onMoney(MoneyEvent e) {

        game.getScoreboardManager().updatePlayer(e.getPlayer());
    }

    @EventHandler
    public void onCountdownEnd(CountdownFinishedEvent e) {
        Bukkit.dispatchCommand(this.game, "minigame " + this.game.getPlayingPlayers().get(0).getName() + " start");

    }

    @EventHandler
    public void onMinigameWin(MinigameWinEvent e) {
        if (this.isAuto()) {
            Bukkit.dispatchCommand(this.game, "minigame " + e.getPlayer().getName() + " stop");
        }
    }

    @EventHandler
    public void onMinigameFound(MinigameFoundEvent e) {
        if (this.isAuto()) {
            Bukkit.dispatchCommand(this.game, "minigame " + e.getPlayer().getName() + " select");
        }
    }

    @EventHandler
    public void onEventFound(EventFoundEvent e) {
        if (this.isAuto()) {
            Bukkit.dispatchCommand(this.game, "event " + e.getPlayer().getName() + " select");
        }
    }

    @EventHandler
    public void onPlayerJailed(PlayerJailedEvent e) {
        if (this.isAuto()) {
            e.getPlayer().teleport(this.game.getFieldManager().getJailField());
            this.game.jailPlayer(e.getPlayer().getFigure());
        }
    }

    @EventHandler
    public void onMoveFinished(MoveFinishedEvent e) {
        if (internalCounter > 0) {
            Bukkit.dispatchCommand(this.game, "dice " + e.getPlayer().getName());
            return;
        }
        Bukkit.getPluginManager().callEvent(new NextPlayerEvent());
        I18nHelper.broadcast("minopoly.gameplay.move_finished", false, e.getPlayer().getDisplay());
        I18nHelper.broadcast("minopoly.ferdinand.move_finished", true);


    }

    @EventHandler
    public void onFieldEvent(FieldEvent e) {
        if (this.isAuto()) {
            if (e.getField().isOwned() && !e.getField().isOwnedBy(e.getPlayer())) {
                int billing = e.getField().getBilling();
                if (e.getField().getColor() == FieldColor.AIRPORT)
                    billing *= this.game.getFieldManager().countProperties(e.getPlayer(), FieldColor.AIRPORT);
                else if (e.getField().getColor() == FieldColor.FUNDS)
                    billing = lastDice
                            * this.game.getFieldManager().countProperties(e.getPlayer(), FieldColor.FUNDS) == 2 ? 10
                            : 4;
                else if (this.game.getFieldManager().hasAll(e.getPlayer(), e.getField().getColor())) {
                    billing *= 2;
                }
                if (game.getSettings().isPayForced())
                    e.getPlayer().transferMoneyTo(e.getField().getOwner(), billing, "Schulden :3");
            } else if (e.getField() instanceof PayingField) {
                e.getPlayer().removeMoney(e.getField().getBilling(), e.getField().toString());
                if (this.game.getSettings().hasPott())
                    this.game.getTHE_POTT_OF_DOOM___andmore_cute_puppies().addMoney(e.getField().getPrice());
            }
        }
    }

    @EventHandler
    public void onNext(NextPlayerEvent e) {
        if (this.isAuto()) {
            internalCounter = 0;
            currentPlayer = getNext();
            Bukkit.dispatchCommand(game, "dice " + currentPlayer.getName() + " x");
            e.getPlayer().teleport(e.getPlayer().getLocation().getLocation(), TeleportCause.UNKNOWN);
        }
    }

    private Player getNext() {
        if (currentPlayer == null)
            return game.getPlayingPlayers().get(0);
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

    private boolean isAuto() {
        return this.game.getSettings().isAuto();
    }

    @EventHandler
    public void onDamageNPC(EntityDamageEvent e) {
        if (e.getEntity().hasMetadata("PlayerNPC") || e.getEntity().hasMetadata(CancelConstants.CANCEL_DAMAGE)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChangeWorld(EntityChangeBlockEvent e) {
        if (e.getEntity().hasMetadata(CancelConstants.CANCEL_BLOCK_CHANGE)) {
            if (e.getTo() != Material.AIR) e.setCancelled(true);
        }
    }

}
