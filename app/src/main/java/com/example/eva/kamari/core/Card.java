package com.example.eva.kamari.core;

import android.support.annotation.NonNull;

/**
 * Created by danleyb2 on 8/12/17.
 */

public class Card implements Comparable<Card> {

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
        StringBuilder cardSB = new StringBuilder();
        // clubs
        // diamonds
        // hearts
        // spades
        cardSB.append("c");
        switch (this.rank) {
            case Ace:
                cardSB.append("a");
                break;
            case Two:
                cardSB.append("2");
                break;
            case Three:
                cardSB.append("3");
                break;
            case Four:
                cardSB.append("4");
                break;
            case Five:
                cardSB.append("5");
                break;
            case Six:
                cardSB.append("6");
                break;
            case Seven:
                cardSB.append("7");
                break;
            case Eight:
                cardSB.append("8");
                break;
            case Nine:
                cardSB.append("9");
                break;
            case Ten:
                cardSB.append("t");
                break;
            case Jack:
                cardSB.append("j");
                break;
            case Queen:
                cardSB.append("q");
                break;
            case King:
                cardSB.append("k");
                break;
        }

        switch (this.suit) {
            case Clubs:
                cardSB.append("c");
                break;
            case Diamonds:
                cardSB.append("d");
                break;
            case Hearts:
                cardSB.append("h");
                break;
            case Spades:
                cardSB.append("s");
                break;
        }

        return cardSB.toString();
    }

    @Override
    public String toString() {
        String s = rank + " of " + suit;
        return s;
    }

    public Requirement[] getCardRequirement() {
        // todo cache, stop processing every time
        return Pack.getRequirements(this);
    }

    public int getAction() {
        // if should pick
        if (
                this.rank == Rank.Ace||
                this.rank == Rank.Jack||
                this.rank == Rank.King||
                this.rank == Rank.Two||
                this.rank == Rank.Three||
                this.rank == Rank.Queen

                ){

            return 0;

        }else {
            return 1;
        }

    }

    public void getValidDeal() {

    }

    public void getCantDealAction() {

    }

    @Override
    public int compareTo(@NonNull Card card) {
        return this.suit == card.suit && this.rank == card.rank?1:0;
    }
}
