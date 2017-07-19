package de.wolfi.minopoly.commands;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;

public class BuyCommand extends CommandInterface {

	public BuyCommand(Main plugin) {
		super(plugin,0,false);
	}

	@Override
	protected void executeCommand(Minopoly board, Player player, String[] args) {

		if(!player.getLocation().isOwned()) player.getLocation().buy(player);
	}

}
