package de.wolfi.minopoly.components;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.bukkit.metadata.FixedMetadataValue;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.commands.BankCommand;
import de.wolfi.minopoly.commands.DiceCommand;
import de.wolfi.minopoly.commands.FieldCommand;
import de.wolfi.minopoly.components.fields.Field;
import de.wolfi.minopoly.events.MoneyEvent;
import de.wolfi.minopoly.utils.Dangerous;
import de.wolfi.minopoly.utils.DisguiseManager;
import de.wolfi.minopoly.utils.FigureType;
import de.wolfi.minopoly.utils.I18nHelper;
import de.wolfi.minopoly.utils.MapFactory;
import de.wolfi.minopoly.utils.Messages;
import de.wolfi.minopoly.utils.TeleportCause;
import de.wolfi.utils.TimedEntity;

public class Player {

	protected final @Dangerous Minopoly game;

	private final org.bukkit.entity.Player hook;

	private Field location;

	private final Bank money;

	private TimedEntity tmp;

	private FigureType type;

	public Player(org.bukkit.entity.Player hook, FigureType t, Minopoly game, Bank bank) {
		this.hook = hook;
		this.type = t;
		this.game = game;
		this.money = bank;
	}

	public void addMoney(int amount) {
		this.addMoney(amount, "Grundlos");
	}

	public void addMoney(int amount, String reason) {
		this.money.addMoney(this, amount);
		Bukkit.getPluginManager().callEvent(new MoneyEvent(this, this.getMoney(), reason));
		Messages.MONEY_GAIN.send(this.hook, String.valueOf(amount), reason);
	}

	public int getMoney() {
		return this.money.getMoney(this);
	}

	@Override
	public int hashCode() {
		return this.getFigure().hashCode();
	}

	@Override
	public boolean equals(Object compare) {
		if (!(compare instanceof Player))
			return false;
		return this.getFigure() == ((Player) compare).getFigure();
	}

	public String getDisplay() {
		return this.getName() + "(" + this.getFigure().getDisplay() + ")";
	}

	public FigureType getFigure() {
		return this.type;
	}

	@Dangerous(y = "Internal use ONLY!")
	public org.bukkit.entity.Player getHook() {
		return this.hook;
	}

	public void sendMessage(String msg, boolean audio, Object... args) {
		I18nHelper.sendMessage(this.getHook(), msg, audio, args);
	}

	/**
	 * Returns the Name of the Hook
	 * 
	 * @return this.hook.getName()
	 */
	public String getName() {
		return this.hook.getName();
	}

	public void move(int amount) {
		Messages.MOVE_STARTED.broadcast(this.getDisplay(), Integer.toString(amount));
		Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
			for (int i = 0; i < amount; i++) {
				final int currentNumber = i;
				Bukkit.getScheduler().runTask(Main.getMain(), () -> {
					Player.this.location = Player.this.game.getFieldManager().getNextField(Player.this.location);
					if (currentNumber < amount - 1)
						Player.this.location.byPass(Player.this);
					Player.this.spawnFigure();
				});
				try {
					Thread.sleep(1000);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Bukkit.getScheduler().runTask(Main.getMain(), () -> Player.this.location.playerStand(Player.this));
		});

	}

	public void removeMoney(int amount, String reason) {
		this.money.removeMoney(this, amount);
		Bukkit.getPluginManager().callEvent(new MoneyEvent(this, this.getMoney(), reason));
		Messages.MONEY_PAYD.send(this.hook, String.valueOf(amount), reason);
	}

	private static final int DICE_SLOT = 8;

	public void selectDiceSlot() {
		this.hook.getInventory().setHeldItemSlot(Player.DICE_SLOT);
	}

	public void activateDice() {
		this.hook.playSound(this.hook.getLocation(), Sound.CHICKEN_EGG_POP, 1F, 7F);
		this.hook.getInventory().setItem(Player.DICE_SLOT, DiceCommand.dice);
	}

	public void setInventory() {
		this.hook.getInventory().clear();

		this.hook.setGameMode(GameMode.ADVENTURE);
		this.hook.setAllowFlight(true);
		this.hook.setFlying(true);

		this.hook.getInventory().addItem(getMap());
		this.hook.getInventory().addItem(GameListener.finishMove);
		this.hook.getInventory().addItem(BankCommand.payGUI);
		this.hook.getInventory().addItem(FieldCommand.fieldGUI);

		this.hook.setSaturation(20);
		this.hook.setHealth(this.hook.getMaxHealth());
	}

	private ItemStack getMap() {

		MapView mapView = MapFactory.getMap(this.game, this.hook);
		@SuppressWarnings("deprecation")
		ItemStack mapItem = new ItemStack(Material.MAP, 1, mapView.getId());
		return mapItem;
	}

	private void spawnFigure() {
		if (this.tmp != null)
			this.tmp.remove();
		// final Entity e =
		// this.location.getWorld().spawnEntity(this.location.getTeleportLocation(),
		// this.type.getEntityType());
		// e.setCustomName(this.hook.getName());
		// e.setCustomNameVisible(true);
		// e.setMetadata("PlayerNPC", new FixedMetadataValue(Main.getMain(),
		// this));
		TimedEntity t = new TimedEntity(this.type.getEntityType(), this.location.getTeleportLocation(), 0)
				.name(this.hook.getName()).nbt("NoAI", 1)
				.metadata("PlayerNPC", new FixedMetadataValue(Main.getMain(), this));

		// if (e instanceof LivingEntity)
		// ((LivingEntity) e).addPotionEffect(new
		// PotionEffect(PotionEffectType.SLOW, 20 * 60 * 60, 20));
		this.tmp = t;
	}

	public Field getLocation() {
		return location;
	}

	public void teleport(Field to) {
		this.location = to;
		this.spawnFigure();
	}

	public void teleport(Location to, TeleportCause cause) {
		this.hook.teleport(to);
		if (cause == TeleportCause.MINIGAME_STARTED) {
			DisguiseManager.disguise(this.hook, this.type);
			if (this.tmp != null)
				this.tmp.remove();
		} else if (cause == TeleportCause.MINIGAME_END) {
			this.setInventory();
			DisguiseManager.vanish(this.hook);
			this.spawnFigure();
		} else if (cause == TeleportCause.MINIGAME_ACTION)
			Messages.TELEPORT.send(this.hook);
	}

	private void transferMoneyFrom(Player player, int amount, String reason) {
		player.addMoney(amount, reason);
		Messages.MONEY_TRANSFER_GAIN.send(this.hook, String.valueOf(amount), player.getDisplay(), reason);
	}

	public void transferMoneyTo(Player player, int amount, String reason) {
		this.removeMoney(amount, reason);
		Messages.MONEY_TRANSFER_SENT.send(this.hook, String.valueOf(amount), player.getDisplay(), reason);
		player.transferMoneyFrom(this, amount, reason);
	}

	public boolean isDummy() {
		return this instanceof DummyPlayer;
	}

	protected void update(SerializeablePlayer s) {
		this.type = s.getF();
		this.location = s.getLoc();
		this.money.checkOut(this);
		this.money.checkIn(this, s.getBankCard());

	}

	protected SerializeablePlayer serialize() {
		return new SerializeablePlayer(this.game, this.location, this.type, this.money.getConsumerID(this));
	}

	public boolean isJailed() {
		return game.isJailed(this.getFigure());
	}

}
