package de.wolfi.minopoly.components.fields;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public enum FieldColor {
	SPECIAL(DyeColor.SILVER,ChatColor.GRAY),
	
	
	BLUE(DyeColor.BLUE,ChatColor.BLUE),
	GREEN(DyeColor.GREEN,ChatColor.GREEN),
	YELLOW(DyeColor.YELLOW,ChatColor.YELLOW),
	RED(DyeColor.RED,ChatColor.RED),
	ORANGE(DyeColor.ORANGE,ChatColor.GOLD),
	PURPLE(DyeColor.PURPLE,ChatColor.LIGHT_PURPLE),
	WHITE(DyeColor.WHITE,ChatColor.WHITE),
	BROWN(DyeColor.BROWN,ChatColor.DARK_GREEN);
	private DyeColor color;
	private ChatColor color2;
	
	private FieldColor(DyeColor color, ChatColor color2){
		this.color = color;
		this.color2 = color2;
	}
	
	public DyeColor getColor() {
		return color;
	}
	
	public ChatColor getColorChat() {
		return color2;
	}
	
}
