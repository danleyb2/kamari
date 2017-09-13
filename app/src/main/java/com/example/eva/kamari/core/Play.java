package com.example.eva.kamari.core;

import java.util.ArrayList;

/**
 * Created by danleyb2 on 9/13/17.
 */

public class Play {
    /*
    Any process followed by a turn

    gameStart
    card(s) pick
    card(s) deal
    jump
    kick back
    Question

     */

    private PlayAction playAction;

    private Player player = null;
    private ArrayList<Card> cards;

    public Play(ArrayList<Card> cards, PlayAction playAction) {
        this.cards = cards;
        this.playAction = playAction;
    }

    public Play(ArrayList<Card> play, Player player, PlayAction playAction) {
        this(play, playAction);
        this.player = player;
    }

    public Play(Card pickedCard, Player player, PlayAction take) {
        setPlayAction(take);
        setPlayer(player);

        cards = new ArrayList<>();
        cards.add(pickedCard);
    }

    public PlayAction getPlayAction() {
        return playAction;
    }

    public void setPlayAction(PlayAction playAction) {
        this.playAction = playAction;
    }

    /**
     * Plays like the starting card play will have a null player
     *
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }


    public Card getLastCard() {
        return getCards().get(getCards().size() - 1);
    }

    @Override
    public String toString() {
        String m = "";

        if (getPlayAction() != PlayAction.START)
            m += getPlayer().getName();

        m += " [" + getPlayAction() + "] ";
        m += getCards();

        return m;
    }
}
