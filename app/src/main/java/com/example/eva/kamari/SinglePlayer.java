package com.example.eva.kamari;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eva.kamari.core.Game;
import com.example.eva.kamari.core.Player;
import com.example.eva.kamari.core.PlayerType;

import java.util.ArrayList;

public class SinglePlayer extends AppCompatActivity {

    private Game game;
    private Player me;

    private Button buttonPlay;
    private TextView textViewOpName;
    private TextView textViewOpCards;
    private ImageView imageViewField;
    private ImageView imageViewDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        textViewOpName = (TextView) findViewById(R.id.textViewOpName);
        textViewOpCards = (TextView) findViewById(R.id.textViewOpCards);
        imageViewField = (ImageView) findViewById(R.id.imageViewField);
        imageViewDeck = (ImageView) findViewById(R.id.imageViewDeck);

        initGame();
        draw();

        imageViewDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                me.give(game.getPack().deal());
                draw();
            }
        });


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
        imageViewField.setImageDrawable(Utils.loadDrawable(this, game.getJustPlayed().getDrawableImage()));

        //TODO draw deck
        // how does an empty deck look?

        //TODO draw cards in hand
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_cards);

        //Player me = game.getMePlayer();

        CardsAdapter mAdapter = new CardsAdapter(this, me);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false
        );
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }


    void initGame() {

        game = new Game();

        me = game.addPlayer("danleyb2", false);
        game.addPlayer(new Player("COMP", PlayerType.CPU, true));

        game.init();

        game.pickStarter();
    }
}
