package de.wolfi.minopoly.utils;

import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;

import de.robingrether.idisguise.disguise.Disguise;
import de.robingrether.idisguise.disguise.DisguiseType;

public enum FigureType {

	WOLF(DisguiseBuilder.create(DisguiseType.WOLF).setCollar(DyeColor.RED).create(),EntityType.WOLF);
	
	private Disguise d;
	private EntityType entityType;
	private FigureType(Disguise d, EntityType en){
		this.d = d;
		this.entityType = en;
	}
	
	public Disguise createDisguise(){
		return d.clone();
	}
	
	public EntityType getEntityType() {
		return entityType;
	}
}
