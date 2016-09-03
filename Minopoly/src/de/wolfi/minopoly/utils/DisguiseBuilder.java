package de.wolfi.minopoly.utils;

import org.bukkit.DyeColor;

import de.robingrether.idisguise.disguise.Disguise;
import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.idisguise.disguise.MobDisguise;
import de.robingrether.idisguise.disguise.WolfDisguise;

public class DisguiseBuilder {

	private Disguise applyTo;
	
	private DisguiseBuilder(){
		
	}
	
	

	public static DisguiseBuilder create(DisguiseType type){
		DisguiseBuilder b = new DisguiseBuilder();
		b.applyTo = type.newInstance();
		return b;
	}
	
	public DisguiseBuilder setCollar(DyeColor color){
		((WolfDisguise)applyTo).setCollarColor(color);
		return this;
	}
	
	
	public DisguiseBuilder setDisplayName(String name){
		((MobDisguise) applyTo).setCustomName(name);
		return this;
	}



	protected Disguise create() {
		// TODO Auto-generated method stub
		return applyTo;
	}
}
