package com.example.eva.kamari;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eva.kamari.core.Card;
import com.example.eva.kamari.core.Game;
import com.example.eva.kamari.core.MyCard;
import com.example.eva.kamari.core.Player;
import com.example.eva.kamari.core.PlayerType;
import com.example.eva.kamari.core.Rank;

import java.util.ArrayList;

public class SinglePlayer extends AppCompatActivity {

    private static final String TAG = SinglePlayer.class.getSimpleName();
    private Game game;
    private Player me;

    private Button buttonPlay;
    private TextView textViewOpName;
    private TextView textViewOpCards;
    private ImageView imageViewField;
    private ImageView imageViewDeck;
    private CardsAdapter mAdapter;


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

        imageViewDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // todo just give me the right count of cards and move on,
                // no need for the confirmation PLAY >
                Card dealt = game.getPack().deal();
                Log.i(TAG, "card pick: " + dealt);
                me.give(dealt);
                takeTurn();
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Card> playSelection = mAdapter.getSelectedItems();

                me.getHand().removeAll(playSelection);
                mAdapter.notifyDataSetChanged();

                boolean played = game.playTurn(playSelection, me);
                if (played) {
                    game.getPlayed().addAll(playSelection);
                } else {
                    //TODO couldn't play, possibly wrong card, display appropriate error
                    Log.e(TAG, "[*] Could not play: " + playSelection);

                    me.getHand().addAll(playSelection);
                }
                takeTurn();
            }
        });

    }


    void draw(boolean isUpdate) {
        Log.i(TAG, "[-] Drawing game     ***        [-]");

        Log.i(TAG, "me " + me.dumpString());

        //TODO draw player
        // for now it's just one play
        ArrayList<Player> opponents = game.getOpponents();
        for (Player player : opponents) {
            Log.i(TAG, "\topponent " + player.dumpString());
        }

        Player opponent = opponents.get(0);
        textViewOpName.setText(opponent.getName());

        //TODO draw player cards count
        textViewOpCards.setText(String.valueOf(opponent.getNumCards()));

        //TODO draw field
        imageViewField.setImageDrawable(Utils.loadDrawable(this, game.getJustPlayed().getDrawableImage()));

        //TODO draw deck
        // how does an empty deck look?

        //TODO draw cards in hand
        //Player me = game.getMePlayer();
        if (!isUpdate) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_cards);

            mAdapter = new CardsAdapter(this, me, new MyViewHolder.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MyCard myCard) {
                    draw(true);
                }
            });
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(
                    this, LinearLayoutManager.HORIZONTAL, false
            );
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }


    }

    void takeTurn() {
        Player aP = game.turn();

        // todo Log.i(ap.validPlays());

        Log.e(TAG, "[-] Take turn *********************[" + aP.getName() + "]**********************[-]");


        if (aP.isOpponent()) {
            Log.i(TAG,"player is Opponent, getting AI play");
            aP.playTurn(game);

            draw(true);
            takeTurn();
        } else {
            draw(true);
            // waiting for me to take turn
            // todo pre-process player actions,
            // e.g pick me cards
            Card jP = game.getJustPlayed();
            if (jP.getRank() == Rank.Two) {

                me.give(game.getPack().deal());
                me.give(game.getPack().deal());

                takeTurn();
            }
            if (jP.getRank() == Rank.Three) {
                me.give(game.getPack().deal());
                me.give(game.getPack().deal());
                me.give(game.getPack().deal());

                takeTurn();
            }

        }
    }


    void initGame() {
        Log.i(TAG, "[-]----------------------------------------------------------------------");
        Log.i(TAG, "[-]\t\t\t  ... KAMARI ....");
        Log.i(TAG, "[-] Welcome to KAMARI ... ##################################### initializing [-]");
        game = new Game();

        me = game.addPlayer("danleyb2", false);
        me.setOnRequestCallback(new UserAction() {
            @Override
            public Card onRequestCard() {


                // new FireMissilesDialogFragment().show();


                return null;
            }
        });

        game.addPlayer(new Player("COMP", PlayerType.CPU, true));

        game.init();
        draw(false);
        takeTurn();

    }



}
