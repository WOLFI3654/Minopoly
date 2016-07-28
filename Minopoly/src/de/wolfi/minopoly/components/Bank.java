package de.wolfi.minopoly.components;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class Bank implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4371376719258260066L;

	private static final transient UUID masterCard = UUID.fromString("0000-0000-0000-0000-0000");
	
	private HashMap<UUID,Integer> store = new HashMap<>();
	
	private transient HashMap<Player,UUID> consumers = new HashMap<>();
	
	public Bank(){
		
	}
	
	protected void load(){
		consumers = new HashMap<>();
	}
	
	public boolean hasMoney(UUID consumerID, int money){
		if(hasMasterCard(consumerID)) return true;
		
		
		return getMoney(consumerID) >= money;
	}
	
	public int getMoney(UUID consumerID){
		if(hasMasterCard(consumerID)) return Integer.MAX_VALUE;
		return store.get(consumerID);
	}
	
	public int getMoney(Player consumer){
		return getMoney(getConsumerID(consumer));
	}
	
	public boolean hasMoney(Player consumer, int money){
		return hasMoney(getConsumerID(consumer),money);
	}
	
	private void setMoney(UUID consumerID, int value){
		if(hasMasterCard(consumerID)) return;
		store.put(consumerID, value);
	}
	
	public void addMoney(UUID consumerID, int amount){
		setMoney(consumerID, getMoney(consumerID)+amount);
	}
	
	public void addMoney(Player consumer, int amount){
		setMoney(getConsumerID(consumer), amount);
	}
	
	public void removeMoney(UUID consumerID, int amount){
		if(hasMasterCard(consumerID)) return;
		setMoney(consumerID, getMoney(consumerID) - amount);
	}
	
	public void removeMoney(Player consumer, int amount){
		removeMoney(getConsumerID(consumer), amount);
	}
	
	public void payMoney(Player sender, Player reciever, int amount){
		payMoney(getConsumerID(sender), getConsumerID(reciever), amount);
	}
	
	public void payMoney(UUID senderID, UUID recieverID, int amount){
		removeMoney(senderID, amount);
		addMoney(recieverID, amount);
	}
	
	private boolean hasMasterCard(UUID consumerID){
		return consumerID.equals(masterCard);
	}
	
	public boolean isConsumer(Player consumer){
		return !hasMasterCard(getConsumerID(consumer));
	}
	
	public void checkIn(Player newConsumer){
		checkIn(newConsumer, UUID.randomUUID());
	}
	
	public void checkIn(Player newConsumer, UUID consumerID){
		if(!isConsumer(newConsumer)){
			consumers.put(newConsumer, consumerID);
			if(!store.containsKey(consumerID)) store.put(consumerID, 2000);
		}		
	}
	
	private UUID getConsumerID(Player consumer){
		if(!consumers.containsKey(consumer)){
			return masterCard;
		}
		return consumers.get(consumer);
	}
		
}

