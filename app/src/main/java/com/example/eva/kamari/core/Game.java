package com.example.eva.kamari.core;

import java.util.ArrayList;

/**
 * Created by danleyb2 on 8/12/17.
 */

public class Game {

    private ArrayList<Player> players = new ArrayList<>();
    private Pack pack;

    private ArrayList<Card> played = new ArrayList<>(54);

    public void init() {
        this.pack = new Pack();
        this.pack.shuffle();
        for (Player player :
                players) {

            for (int i = 0; i < 4; i++) {
                player.give(pack.deal());
            }

        }
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

    public void pickStarter() {
        players.get(0);//.takeTurn();

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
}
