package de.wolfi.minopoly.components.fields;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.GameObject;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Dangerous;
import de.wolfi.minopoly.utils.Messages;

public abstract class Field extends GameObject {

	private static final HashMap<FieldColor, ArrayList<Field>> members = new HashMap<>();

	/**
	 * 
	 */
	private final int r = 2;

	private static final long serialVersionUID = 2119752416278230984L;

	private static final void add(FieldColor color2, Field field) {
		ArrayList<Field> l = Field.members.get(color2);
		if (l == null)
			l = new ArrayList<>();
		if (!l.contains(field))
			l.add(field);
		Field.members.put(color2, l);
	}

	private final FieldColor color;
	protected final Minopoly game;

	private final boolean isOwned = false;

	private transient Location location;
	private transient Location tp;
	private final String name;

	private Player owner;

	private final HashMap<String, Object> storedLocation;

	public Field(String name, FieldColor color, Location l, Minopoly game) {
		this.color = color;
		this.name = name;
		this.storedLocation = new HashMap<>(l.serialize());
		this.location = l;
		this.game = game;
		Field.add(color, this);

		this.load();
	}

	public abstract void byPass(Player player);

	@SuppressWarnings("deprecation")
	protected void getCircle(int amount, int yAdd, boolean falling, MaterialData m) {
		final World w = this.location.getWorld();

		// final double increment = (2 * Math.PI) / amount;

		for (double x = -r; x <= r; x++) {

			for (double z = -r; z <= r; z++) {

				final Location l = new Location(w, this.location.getX() + x, this.location.getY() + yAdd,
						this.location.getZ() + z);
				if (this.location.distance(l) > this.r)
					continue;
				l.getBlock().setType(m.getItemType());
				l.getBlock().setData(m.getData(), false);
				//				l.getBlock().setType(m.getItemType());
//				l.getBlock().setData(m.getData());
				if (falling)
					w.spawnEntity(l, EntityType.FALLING_BLOCK);
			}
		}
	}

	public FieldColor getColor() {
		return this.color;
	}

	public Location getLocation() {
		return this.location;
	}
	
	public Location getTeleportLocation(){
		return this.tp;
	}

	private String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.getColor().getColorChat() + this.getName();
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Player getOwner() {
		return this.owner;
	}

	public World getWorld() {
		return this.location.getWorld();
	}

	public boolean isOwned() {
		return this.isOwned;
	}

	@Dangerous(y = "Internal use ONLY!")
	@Override
	public void load() {
		this.location = Location.deserialize(this.storedLocation);
		this.tp = this.location.clone().add(0,1,0);
		this.spawn();
	}

	public void playerStand(Player player) {
		if (this.isOwned())
			if (!this.owner.equals(player))
				Messages.OTHER_FIELD_ENTERED.broadcast(player.getName(), this.owner.getName(),
						this.color.getColorChat() + this.getName());
	}

	public abstract void spawn();

	public boolean isOwnedBy(Player player) {
		return this.isOwned() && this.getOwner().equals(player);
	}

	@Dangerous(y = "Internal use ONLY!")
	@Override
	public void unload() {
	}

}
