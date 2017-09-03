package com.example.eva.kamari;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eva.kamari.core.Game;
import com.example.eva.kamari.core.Player;
import com.example.eva.kamari.core.PlayerType;

import java.util.ArrayList;

public class SinglePlayer extends AppCompatActivity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        initGame();
    }

    void draw() {

        //TODO draw player
        // for now it's just one play
        ArrayList<Player> opponents = game.getOpponents();
        Player opponent = opponents.get(0);


        //TODO draw player cards count

        //TODO draw field

        //TODO draw deck

        //TODO draw cards in hand

    }

    void initGame() {

        game = new Game();

        game.addPlayer("danleyb2", false);
        game.addPlayer(new Player("COMP", PlayerType.CPU, true));

        game.init();

        game.pickStarter();
    }
}
