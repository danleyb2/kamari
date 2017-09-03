package com.example.eva.kamari;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eva.kamari.core.Game;
import com.example.eva.kamari.core.Player;
import com.example.eva.kamari.core.PlayerType;

import java.util.ArrayList;

public class SinglePlayer extends AppCompatActivity {

    private Game game;


    private TextView textViewOpName;
    private TextView textViewOpCards;
    private ImageView imageViewField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);


        textViewOpName = (TextView) findViewById(R.id.textViewOpName);
        textViewOpCards = (TextView) findViewById(R.id.textViewOpCards);
        imageViewField = (ImageView) findViewById(R.id.imageViewField);

        initGame();
        draw();
    }


    Drawable loadDrawable(String name) {
        Drawable drawable = getResources()
                .getDrawable(
                        getResources().getIdentifier(name, "drawable", getPackageName())
                );

        return drawable;

    }

    void draw() {

        //TODO draw player
        // for now it's just one play
        ArrayList<Player> opponents = game.getOpponents();
        Player opponent = opponents.get(0);
        textViewOpName.setText(opponent.getName());

        //TODO draw player cards count
        textViewOpCards.setText(String.valueOf(opponent.getNumCards()));



        //TODO draw field
        imageViewField.setImageDrawable(loadDrawable(game.getJustPlayed().getDrawableImage()));






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
