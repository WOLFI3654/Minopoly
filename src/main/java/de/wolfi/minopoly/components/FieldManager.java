package de.wolfi.minopoly.components;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.fields.Field;
import de.wolfi.minopoly.components.fields.FieldColor;
import de.wolfi.minopoly.components.fields.JailField;
import de.wolfi.minopoly.components.fields.NormalField;
import de.wolfi.minopoly.components.fields.StartField;
import de.wolfi.utils.ItemBuilder;

public class FieldManager extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6097015591168469852L;

	private final ArrayList<Field> fields = new ArrayList<>();
	private final HashMap<FieldColor, ArrayList<Field>> mappedList = new HashMap<>();

	private static final Enchantment owned = new ItemBuilder.MyEnchantment("Owned");

	protected FieldManager() {
	}

	public Field addField(Field f) {
		f.spawn();
		this.fields.add(f);
		this.addMapped(f.getColor(), f);
		return f;
	}

	private void addMapped(FieldColor color, Field f) {
		ArrayList<Field> list = this.mappedList.get(color);
		if (list == null)
			list = new ArrayList<>();
		if (!list.contains(f))
			list.add(f);
		this.mappedList.put(color, list);
	}

	public Field getNextField(Field from) {
		boolean next = false;
		for (final Field f : this.fields) {
			if (f.equals(from)) {
				next = true;
				continue;
			}
			if (next)
				return f;
		}

		return this.fields.get(0);
	}

	@Override
	protected void load() {
		for (final Field f : this.fields)
			f.load();
	}

	public int countProperties(Player player, FieldColor color) {
		int count = 0;
		for (Field f : mappedList.get(color)) {
			if (f.isOwnedBy(player))
				count++;
		}
		return count;
	}

	@Override
	protected void unload() {
		for (final Field f : this.fields)
			f.unload();

	}

	public ItemStack createFieldInfo(Player p, Field f) {
		MaterialData data = f.getBlock();
		ItemBuilder field = new ItemBuilder(data.toItemStack());
		field.setName(f.toString());
		field.addLore("Typ: " + f.getClass().getSimpleName());
		if (f instanceof NormalField)
			field.addLore("Farbe: " + f.getColor().toString());
		if (f.getPrice() >= 0) {
			field.addLore("Preis: " + f.getPrice());
			field.addLore("Steuern: " + f.getBilling());
		}

		if (f.isOwned()) {
			Player owner = f.getOwner();

			field.addLore("Owner: " +( owner != null ? owner.getDisplay() : f.getTypeOwner().getDisplay()));

		} else
			field.addLore("§aVerfügbar");
		if (f.isOwnedBy(p))
			field.enchant(owned, 10);

		StringBuilder spieler = new StringBuilder();
		int amount = 0;
		for (Player pi : f.getGame().getPlayingPlayers()) {
			if (pi.getLocation().equals(f)) {
				if (spieler.length() != 0)
					spieler.append(',');
				spieler.append(pi.getDisplay());
				amount++;
			}
		}
		field.addLore(spieler.toString());
		field.setAmount(amount);
		return field.build();
	}

	public Field getFieldByType(Field start, Class<? extends Field> type) {
		boolean found = false;
		Field next = null;
		if (start != null)
			for (final Field f : this.fields) {
				if (f.equals(start)) {
					next = f;
					break;
				}
			}
		while (found == false) {
			next = getNextField(next);
			if (next.getClass() == type)
				found = true;
		}

		return next;
	}

	public Field getFieldByString(Field start, String toString) {
		Bukkit.broadcastMessage("Searching " + toString);
		boolean found = false;
		Field next = null;
		if (start != null)
			for (final Field f : this.fields) {
				if (f.equals(start)) {
					next = f;
					break;
				}
			}
		while (found == false) {
			next = getNextField(next);
			Bukkit.broadcastMessage(next.toString());
			if (next.toString().equalsIgnoreCase(toString))
				found = true;
		}

		return next;
	}

	public Field getStartField() {
		return this.getFieldByType(null, StartField.class);
	}

	public Field getJailField() {
		return this.getFieldByType(null, JailField.class);
	}

	public boolean hasAll(Player player, FieldColor color) {
		return this.countProperties(player, color) >= this.mappedList.get(color).size();
	}

	public ArrayList<Field> getFields() {
		return new ArrayList<>(this.fields);
	}
}
