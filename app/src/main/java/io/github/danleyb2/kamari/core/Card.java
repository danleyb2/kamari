package io.github.danleyb2.kamari.core;

/**
 * Created by danleyb2 on 8/12/17.
 */

class Card {

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

    @Override
    public String toString() {
        String s = rank + " of " + suit;
        return s;
    }
}
