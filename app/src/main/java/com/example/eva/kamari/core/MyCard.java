package com.example.eva.kamari.core;

/**
 * Created by danleyb2 on 9/3/17.
 */

public class MyCard extends Card {

    private boolean isSelected = false;

    public MyCard(Card card) {
        this(card.getRank(), card.getSuit());
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public MyCard(Rank rank, Suit suit) {
        super(rank, suit);
    }
}
