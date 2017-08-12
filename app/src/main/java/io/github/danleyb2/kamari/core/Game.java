package io.github.danleyb2.kamari.core;

import java.util.ArrayList;

/**
 * Created by danleyb2 on 8/12/17.
 */

public class Game {

    private ArrayList<Player> players = new ArrayList<>();
    private Pack pack;

    public void init() {
        this.pack = new Pack();
        this.pack.shuffle();
        for (Player player :
                players) {

            for (int i = 0; i < 4; i++) {
                player.give(pack.deal());
            }

        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addPlayer(String playerName) {
        addPlayer(new Player(playerName, PlayerType.HUMAN));
    }

    public void pickStarter() {
        players.get(0);//.takeTurn();

    }
}
