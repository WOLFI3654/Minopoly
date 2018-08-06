package de.wolfi.minopoly.components;

import java.util.HashMap;
import java.util.UUID;

public class Bank extends GameObject {

    /**
     *
     */
    private static final long serialVersionUID = -4371376719258260066L;
    private static final UUID masterCard = UUID.fromString("0000-0000-0000-0000-0000");

    private final HashMap<UUID, Integer> store = new HashMap<>();

    private transient HashMap<Player, UUID> consumers = new HashMap<>();


    public Bank() {

    }

    public void addMoney(Player consumer, int amount) {
        this.addMoney(this.getConsumerID(consumer), amount);
    }

    public void addMoney(UUID consumerID, int amount) {
        this.setMoney(consumerID, this.getMoney(consumerID) + amount);
    }

    public UUID checkIn() {
        return this.checkIn(UUID.randomUUID());
    }

    public UUID checkIn(Player newConsumer) {
        return this.checkIn(newConsumer, UUID.randomUUID());
    }

    public UUID checkIn(UUID consumerID) {
        if (!this.store.containsKey(consumerID))
            this.store.put(consumerID, 2000);
        return consumerID;
    }

    public UUID checkIn(Player newConsumer, UUID consumerID) {
        if (!this.isConsumer(newConsumer)) {
            this.consumers.put(newConsumer, consumerID);
            this.checkIn(consumerID);
        }
        return consumerID;
    }

    public boolean checkOut(Player consumer) {
        if (this.isConsumer(consumer)) {
            this.consumers.remove(consumer);
            return true;
        }
        return false;
    }

    protected UUID getConsumerID(Player consumer) {
        if (!this.consumers.containsKey(consumer))
            return Bank.masterCard;
        return this.consumers.get(consumer);
    }

    public int getMoney(Player consumer) {
        return this.getMoney(this.getConsumerID(consumer));
    }

    public int getMoney(UUID consumerID) {
        if (this.hasMasterCard(consumerID))
            return Integer.MAX_VALUE;
        return this.store.get(consumerID);
    }

    private boolean hasMasterCard(UUID consumerID) {
        return consumerID.equals(Bank.masterCard);
    }

    public boolean hasMoney(Player consumer, int money) {
        return this.hasMoney(this.getConsumerID(consumer), money);
    }

    public boolean hasMoney(UUID consumerID, int money) {
        if (this.hasMasterCard(consumerID))
            return true;

        return this.getMoney(consumerID) >= money;
    }

    public boolean isConsumer(Player consumer) {
        return !this.hasMasterCard(this.getConsumerID(consumer));
    }

    @Override
    protected void load() {
        this.consumers = new HashMap<>();
    }

    public void payMoney(Player sender, Player reciever, int amount) {
        this.payMoney(this.getConsumerID(sender), this.getConsumerID(reciever), amount);
    }

    public void payMoney(UUID senderID, UUID recieverID, int amount) {
        this.removeMoney(senderID, amount);
        this.addMoney(recieverID, amount);
    }

    public void removeMoney(Player consumer, int amount) {
        this.removeMoney(this.getConsumerID(consumer), amount);
    }

    public void removeMoney(UUID consumerID, int amount) {
        if (this.hasMasterCard(consumerID))
            return;
        this.setMoney(consumerID, this.getMoney(consumerID) - amount);
    }

    private void setMoney(UUID consumerID, int value) {
        if (this.hasMasterCard(consumerID))
            return;
        this.store.put(consumerID, value);
    }

    @Override
    protected void unload() {
        // TODO Auto-generated method stub
    }

}
