package de.wolfi.minopoly.utils;

import org.bukkit.DyeColor;

import de.robingrether.idisguise.disguise.Disguise;
import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.idisguise.disguise.MobDisguise;
import de.robingrether.idisguise.disguise.SheepDisguise;
import de.robingrether.idisguise.disguise.WolfDisguise;

public class DisguiseBuilder {

	public static DisguiseBuilder create(DisguiseType type) {
		final DisguiseBuilder b = new DisguiseBuilder();
		b.applyTo = type.newInstance();
		return b;
	}

	private Disguise applyTo;

	private DisguiseBuilder() {

	}

	protected Disguise create() {
		// TODO Auto-generated method stub
		return this.applyTo;
	}

	public DisguiseBuilder setColor(DyeColor color){
		((SheepDisguise) this.applyTo).setColor(color);
		return this;
	}
	
	public DisguiseBuilder setCollar(DyeColor color) {
		((WolfDisguise) this.applyTo).setCollarColor(color);
		return this;
	}

	public DisguiseBuilder setDisplayName(String name) {
		((MobDisguise) this.applyTo).setCustomName(name);
		return this;
	}
}
