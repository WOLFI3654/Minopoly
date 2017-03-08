package de.wolfi.minopoly.components;

import java.io.Serializable;
import java.util.UUID;

import de.wolfi.minopoly.components.fields.Field;
import de.wolfi.minopoly.utils.FigureType;

public class SerializeablePlayer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8329892097368700190L;
	private final FigureType f;
	private final Field loc;
	private final UUID bankCard;
	private final Minopoly board;
	private boolean selected;

	
	public SerializeablePlayer(Minopoly board, Field loc, FigureType f, UUID bankCard) {
		this(board,loc,f,bankCard,false);
	}
	
	protected SerializeablePlayer(Minopoly board, Field loc, FigureType f, UUID bankCard, boolean selected) {
		this.loc = loc;
		this.f = f;
		this.bankCard = bankCard;
		this.board = board;
		this.selected = selected;
	}

	
	public UUID getBankCard() {
		return bankCard;
	}
	
	public FigureType getF() {
		return this.f;
	}

	public Field getLoc() {
		return this.loc;
	}
	
	public Minopoly getBoard() {
		return board;
	}
	
	public Player getPlayer(){
		if(isSelected()){
			return this.board.getByFigureType(this.f);
		}
		return null;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isSelected() {
		return selected;
	}

}
