package de.wolfi.minopoly.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import de.wolfi.minopoly.components.fields.Field;
import de.wolfi.minopoly.components.fields.FieldColor;

public class FieldManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6097015591168469852L;

	
	private final ArrayList<Field> fields = new ArrayList<>();
	private final HashMap<FieldColor,ArrayList<Field>> mappedList = new HashMap<>();
	
	protected FieldManager() {}
	
	public void addField(Field f) {
		f.spawn();
		this.fields.add(f);
		this.addMapped(f.getColor(),f);
	}
	
	private void addMapped(FieldColor color, Field f) {
		ArrayList<Field> list = this.mappedList.get(color);
		if(list == null) list = new ArrayList<>();
		if(!list.contains(f)) list.add(f);
	}

	public Field getNextField(Field from) {
		boolean next = false;
		for (final Field f : this.fields) {
			if (f.equals(from)) {
				next = true;
				continue;
			}
			if (next)
				return from;
		}
		if (!next)
			return null;
		return this.fields.get(0);
	}
	
	protected void load(){
		for (final Field f : this.fields)
			f.load();
	}
	
	public int countProperties(Player player, FieldColor color){
		int count = 0;
		for(Field f : mappedList.get(color)){
			if(f.isOwnedBy(player)) count++;
		}
		return count;
	}
}
