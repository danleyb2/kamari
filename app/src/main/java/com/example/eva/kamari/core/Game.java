package com.example.eva.kamari.core;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by danleyb2 on 8/12/17.
 */

public class Game {

    private static final String TAG = Game.class.getSimpleName();

    private ArrayList<Player> players = new ArrayList<>();
    private Pack pack;

    private ArrayList<Play> played = new ArrayList<>();

    private int activeTurn = 0;
    private int direction = 1;
    private Card requestedCard = null;

    public void init() {
        this.pack = new Pack();
        Log.i(TAG, "Shuffling pack ");
        this.pack.shuffle();

        Log.i(TAG, "Assigning initial cards to players");
        for (Player player :
                players) {

            for (int i = 0; i < 3; i++) {
                player.give(pack.deal());
            }

            Log.i(TAG, player.dumpString());
        }

        //TODO 1. The starting card can be any except a A, 2, 3, 8, J, Q, K or a joker

        Card cardStarting = pack.deal();
        Log.i(TAG,"Starting card :"+cardStarting);
        while (cardStarting.getRank() == Rank.Ace ||
                cardStarting.getRank() == Rank.Two||
                cardStarting.getRank() == Rank.Three ||
                cardStarting.getRank() == Rank.Eight ||
                cardStarting.getRank() == Rank.Jack ||
                cardStarting.getRank() == Rank.Queen ||
                cardStarting.getRank() == Rank.King ) {


            Log.i(TAG,"!! Invalid Starting card :"+cardStarting);
            Log.i(TAG,"Shifting pack and adding to bottom :");
            pack.addToBottom(cardStarting);

            cardStarting = pack.deal();
            Log.i(TAG, "New Starting card :" + cardStarting);
        }

        ArrayList<Card> playS = new ArrayList<>();
        playS.add(cardStarting);

        played.add(new Play(playS, PlayAction.START));
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player addPlayer(String playerName, boolean isOpponent) {
        Player player = new Player(playerName, PlayerType.HUMAN, isOpponent);

        addPlayer(player);
        return player;
    }

    public Player turn() {
        this.activeTurn += direction;
        if (Math.abs(this.activeTurn) == players.size())
            this.activeTurn = 0;

        return players.get(this.activeTurn);

    }

    public ArrayList<Player> getOpponents() {
        ArrayList<Player> opponents = new ArrayList<>();
        for (Player pl :
                players) {
            if (pl.isOpponent())
                opponents.add(pl);
        }


        return opponents;
    }

    public Card getJustPlayed() {
        Play lastPlay = played.get(played.size() - 1);
        return lastPlay.getLastCard();
    }

    public Play getLastPlay() {
        return played.get(played.size() - 1);
    }

    public Player getMePlayer() {
        for (Player pl :
                players) {
            if (!pl.isOpponent())
                return pl;
        }


        // this should not happen
        return null;
    }

    public Pack getPack() {
        return pack;
    }

    public ArrayList<Play> getPlayed() {
        return played;
    }

    public boolean playTurn(ArrayList<Card> playSelection, Player player) {
        Log.i(TAG,"playSelection = [" + playSelection + "], player = [" + player + "]");
        Card facing = this.getJustPlayed();
        Log.i(TAG, "facing Card: " + facing.toString());

        //todo now only support playing single cards
        Card played = playSelection.get(0);



        if (facing.getSuit() == played.getSuit()) {
            return true;
        }

        if (facing.getRank() == played.getRank()) {
            return true;
        }

        return false;
    }

    public void requestCard(Card cardRequest) {
        this.requestedCard = cardRequest;
    }

    public Card requestedCard() {
        return requestedCard;
    }

    public void jump() {
        this.turn();
    }

    public void kickBack() {
        this.direction*=-1;
    }


    public String historyLog() {
        StringBuilder plS = new StringBuilder();

        for (Play pl :
                getPlayed()) {
            plS.append(pl).append("\n");

        }

        return plS.toString();
    }
}
