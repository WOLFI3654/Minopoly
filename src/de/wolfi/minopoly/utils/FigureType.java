package de.wolfi.minopoly.utils;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import de.robingrether.idisguise.disguise.Disguise;
import de.robingrether.idisguise.disguise.DisguiseType;
import de.wolfi.utils.ItemBuilder;

public enum FigureType {

	WOLF("Wolf", DisguiseBuilder.create(DisguiseType.WOLF).setCollar(DyeColor.RED).create(), EntityType.WOLF, new ItemBuilder(Material.BONE).build()),
	SHEEP("Blue Sheep", DisguiseBuilder.create(DisguiseType.SHEEP).setColor(DyeColor.BLUE).create(),EntityType.SHEEP, new ItemBuilder(Material.WOOL).dye(DyeColor.BLUE).build());

	private Disguise d;
	private EntityType entityType;
	private ItemStack item;
	private String name;

	private FigureType(String name, Disguise d, EntityType en, ItemStack item) {
		this.d = d;
		this.entityType = en;
		this.item = item;
		this.name = name;
	}

	public Disguise createDisguise() {
		return this.d.clone();
	}

	public EntityType getEntityType() {
		return this.entityType;
	}

	public String getDisplay(){
		return this.name;
	}
	
	public String getName() {
		return this.toString();
	}

	public ItemStack getItem() {
		return item;
	}
}
