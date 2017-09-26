package de.wolfi.minopoly.events;

import de.wolfi.minopoly.components.Player;

public class DiceEvent extends MinopolyEvent{

	
	private int one,two;
	public DiceEvent(Player player, int one, int two) {
		super(player);
		this.one = one; this.two = two;
	}

	
	public int getOne() {
		return one;
	}
	public int getTwo() {
		return two;
	}
	
	public boolean isPasch(){
		return one == two;
	}
	
	public void setOne(int one) {
		this.one = one;
	}
	public void setTwo(int two) {
		this.two = two;
	}
}
