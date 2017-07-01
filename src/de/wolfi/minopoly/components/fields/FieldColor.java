package de.wolfi.minopoly.components.fields;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public enum FieldColor {

	BLUE(DyeColor.BLUE, ChatColor.BLUE,2), BROWN(DyeColor.BROWN, ChatColor.DARK_GREEN,2), GREEN(DyeColor.GREEN,
			ChatColor.GREEN,3), ORANGE(DyeColor.ORANGE, ChatColor.GOLD,3), PURPLE(DyeColor.PURPLE,
					ChatColor.DARK_PURPLE,3), RED(DyeColor.RED, ChatColor.RED,3), SPECIAL(DyeColor.SILVER,
							ChatColor.GRAY,0), WHITE(DyeColor.WHITE,
									ChatColor.WHITE,3), YELLOW(DyeColor.YELLOW, ChatColor.YELLOW,3);

	public static FieldColor getByColor(DyeColor dye) {
		FieldColor color = FieldColor.SPECIAL;
		for (FieldColor value : FieldColor.values()) {
			if (value.color.equals(dye)) {
				color = value;
				break;
			}
		}
		return color;
	}

	private DyeColor color;
	private ChatColor color2;
	private final int maxFields,r;

	private FieldColor(DyeColor color, ChatColor color2, int max) {
		this.color = color;
		this.color2 = color2;
		this.maxFields = max;
		this.r = 2;
	}
	
	private FieldColor(DyeColor color, ChatColor color2, int max, int r) {
		this.color = color;
		this.color2 = color2;
		this.maxFields = max;
		this.r = r;
	}

	public DyeColor getColor() {
		return this.color;
	}

	public ChatColor getColorChat() {
		return this.color2;
	}
	
	public int getMaxFields() {
		return maxFields;
	}
	
	public int getR() {
		return r;
	}

}
