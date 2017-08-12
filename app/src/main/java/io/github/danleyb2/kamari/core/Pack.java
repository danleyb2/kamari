package io.github.danleyb2.kamari.core;

import java.util.Random;

/**
 * Created by danleyb2 on 8/12/17.
 */

enum Suit {
    Clubs,
    Diamonds,
    Hearts,
    Spades;
}

enum Rank {
    Two,
    Three,
    Four,
    Five,
    Six,
    Seven,
    Eight,
    Nine,
    Ten,
    Jack,
    Queen,
    King,
    Ace;
}

class Pack {

    private int numCards = 52;
    private Card[] deck = new Card[numCards];


    public Pack() {
        fill();
    }

    private void fill() {
        int index = 0;
        for (Rank r :
                Rank.values()) {
            for (Suit s :
                    Suit.values()) {
                deck[index] = new Card(r, s);
                index++;
            }
        }
    }

    public Card deal() {
        this.numCards--;
        return this.deck[numCards];
        this.deck[numCards] = null;
    }

    // modified from
    // https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
    public void shuffle() {
        Random rnd = new Random();
        for (int i = this.numCards - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);

            // Simple swap
            Card a = deck[index];
            deck[index] = deck[i];
            deck[i] = a;
        }
    }

}
