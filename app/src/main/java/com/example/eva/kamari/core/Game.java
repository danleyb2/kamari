package com.example.eva.kamari.core;

import java.util.ArrayList;

/**
 * Created by danleyb2 on 8/12/17.
 */

public class Game {

    private ArrayList<Player> players = new ArrayList<>();
    private Pack pack;

    private ArrayList<Card> played = new ArrayList<>(54);

    private int activeTurn = 0;
    private int direction = 1;

    public void init() {
        this.pack = new Pack();
        this.pack.shuffle();
        for (Player player :
                players) {

            for (int i = 0; i < 3; i++) {
                player.give(pack.deal());
            }

        }

        //TODO 1. The starting card can be any except a A, 2, 3, 8, J, Q, K or a joker
        played.add(pack.deal());
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
        if (this.activeTurn == players.size())
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

    public ArrayList<Card> getPlayed() {
        return played;
    }

    public boolean playTurn(ArrayList<Card> playSelection, Player player) {
        Card facing = this.getJustPlayed();

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
}
