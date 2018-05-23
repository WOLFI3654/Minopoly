package de.wolfi.minopoly.components;

import java.io.Serializable;

public abstract class GameObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected abstract void load();
	protected abstract void unload();
}
