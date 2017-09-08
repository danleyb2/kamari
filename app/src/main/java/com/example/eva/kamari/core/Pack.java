package com.example.eva.kamari.core;

import java.util.ArrayList;
import java.util.Random;

public class Pack {

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

                Card card = new Card(r, s);




                deck[index] = card;




                index++;
            }
        }
    }

    public Card deal() {
        this.numCards--;

        Card toReturn = this.deck[numCards];
        this.deck[numCards] = null;

        return toReturn;

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

    public static void main(String[] args) {
        System.out.println(Rank.Ace);

        ArrayList<Card> arrayList = new ArrayList<>(54);
        System.out.println(arrayList.size());
    }

    public static Requirement[] getRequirements(Card card) {
        Requirement[] requirements = new Requirement[9];

        switch (card.getRank()){
            case Ace:
                break;
            case Two:
                Requirement tmp = new Requirement();
                tmp.acceptsRank(Rank.Two);
                requirements[0] = tmp;
                break;
        }


        switch (card.getSuit()){
            case Clubs:
                Requirement tmp = new Requirement();
                tmp.acceptsSuit(Suit.Clubs);
                requirements[1] = tmp;
                break;
        }

        return new Requirement[0];
    }

    public void addToBottom(Card card) {
        System.arraycopy(deck, 0, deck, 1, deck.length-2);
        deck[0] = card;

    }
}
