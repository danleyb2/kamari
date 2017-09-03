package com.example.eva.kamari.core;


public class Player {
    private String name;
    private Card[] hand = new Card[52];
    private int numCards = -1;
    private PlayerType type;
    private boolean isOpponent = true;

    public Player(String playerName, PlayerType type, boolean isOpponent) {
        this.name = playerName;
        this.type = type;
        this.isOpponent = isOpponent;
    }

    public String getName() {
        return name;
    }

    public void give(Card card) {
        this.numCards++;
        this.hand[this.numCards] = card;
    }

    public boolean isOpponent() {
        return isOpponent;
    }
}
