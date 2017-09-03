package com.example.eva.kamari.core;

/**
 * Created by danleyb2 on 8/12/17.
 */

public class Card {

    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public String getDrawableImage() {
        return "";
    }

    @Override
    public String toString() {
        String s = rank + " of " + suit;
        return s;
    }
}
