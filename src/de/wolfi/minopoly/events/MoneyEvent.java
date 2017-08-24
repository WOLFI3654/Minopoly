package de.wolfi.minopoly.events;

import de.wolfi.minopoly.components.Player;

public class MoneyEvent extends MinopolyEvent{

	private String reason;
	private int sum;

	public MoneyEvent(Player player, int sum, String reason) {
		super(player);
		this.sum = sum;
		this.reason = reason;
	}

	
	public String getReason() {
		return reason;
	}
	public int getSum() {
		return sum;
	}
}
