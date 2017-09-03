package com.example.eva.kamari.core;


import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand = new ArrayList<>(52);
    // private int numCards = -1;
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

    public int getNumCards() {
        return hand.size();
    }

    public void give(Card card) {
        //this.numCards++;
        if (isOpponent) {
            this.hand.add(card);
        } else {
            this.hand.add(new MyCard(card));
        }
    }

    public boolean isOpponent() {
        return isOpponent;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}
