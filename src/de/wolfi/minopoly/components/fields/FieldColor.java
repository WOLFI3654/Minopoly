package de.wolfi.minopoly.components.fields;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public enum FieldColor {

	BLUE(DyeColor.BLUE, ChatColor.BLUE), BROWN(DyeColor.BROWN, ChatColor.DARK_GREEN), GREEN(DyeColor.GREEN,
			ChatColor.GREEN), ORANGE(DyeColor.ORANGE, ChatColor.GOLD), PURPLE(DyeColor.PURPLE,
					ChatColor.LIGHT_PURPLE), RED(DyeColor.RED, ChatColor.RED), SPECIAL(DyeColor.SILVER,
							ChatColor.GRAY), WHITE(DyeColor.WHITE,
									ChatColor.WHITE), YELLOW(DyeColor.YELLOW, ChatColor.YELLOW);

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

	private FieldColor(DyeColor color, ChatColor color2) {
		this.color = color;
		this.color2 = color2;
	}

	public DyeColor getColor() {
		return this.color;
	}

	public ChatColor getColorChat() {
		return this.color2;
	}

}
