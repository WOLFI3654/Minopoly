package de.wolfi.minopoly.events;

import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.components.fields.Field;

public class FieldEvent extends MinopolyEvent{

	private Field field;

	public FieldEvent(Player player, Field field) {
		super(player);
		this.field = field;
	}
	
	public Field getField() {
		return field;
	}

}
