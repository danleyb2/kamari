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
import com.example.eva.kamari.core.Pack;
import com.example.eva.kamari.core.Play;
import com.example.eva.kamari.core.PlayAction;
import com.example.eva.kamari.core.Player;
import com.example.eva.kamari.core.PlayerType;
import com.example.eva.kamari.core.Rank;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    static {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .tag("GP")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        textViewOpName = (TextView) findViewById(R.id.textViewOpName);
        textViewOpCards = (TextView) findViewById(R.id.textViewOpCards);
        imageViewField = (ImageView) findViewById(R.id.imageViewField);
        imageViewDeck = (ImageView) findViewById(R.id.imageViewDeck);

        // logger();

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
                    game.getPlayed().add(new Play(playSelection, me, PlayAction.GIVE));
                } else {
                    // TODO couldn't play, possibly wrong card, display appropriate error
                    Log.e(TAG, "[*] Could not play: " + playSelection);

                    me.getHand().addAll(playSelection);
                }
                takeTurn();
            }
        });

    }

    void logger(){
        Log.d("Tag", "I'm a log which you don't see easily, hehe");
        Log.d("json content", "{ \"key\": 3, \n \"value\": something}");
        Log.d("error", "There is a crash somewhere or any warning");

        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("message");

        Logger.clearLogAdapters();


        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(3)        // (Optional) Skips some method invokes in stack trace. Default 5
//        .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("My custom tag")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        Logger.addLogAdapter(new DiskLogAdapter());


        Logger.w("no thread info and only 1 method");

        Logger.clearLogAdapters();
        formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        Logger.i("no thread info and method info");

        Logger.t("tag").e("Custom tag for only one use");

        Logger.json("{ \"key\": 3, \"value\": something}");

        Logger.d(Arrays.asList("foo", "bar"));

        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("key1", "value2");

        Logger.d(map);

        Logger.clearLogAdapters();
        formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .tag("MyTag")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        Logger.w("my log message with my tag");
    }



    void draw(boolean isUpdate) {

        StringBuilder gameString = new StringBuilder("[-] Drawing game     ***        [-]\n")
                .append("me ").append(me.dumpString()).append("\n");

        //TODO draw player
        // for now it's just one play
        ArrayList<Player> opponents = game.getOpponents();
        for (Player player : opponents) {
            gameString.append("\topponent ").append(player.dumpString());
        }

        Logger.i(gameString.toString());

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

        Log.i(TAG, game.historyLog());


    }

    /**
     * This is the game loop, a recursive calls that stops then the turn player is not COMP
     */
    void takeTurn() {
        // -------------------

        // playerTurnCb
        // game.takeTurn(playerTurn,drawCb,playerQuestionCb,playerPickCb);

        // -----------------
        game.takeTurn(new Game.PlayCb(){
            @Override
            public void onDraw(Game game) {
                draw(true);
            }

            @Override
            public void playerTurn(Game game) {

            }
        });

        // draw(true);
    }


    void initGame() {
        Logger.t("GP").i("[-]----------------------------------------------------------------------\n"+
                "[-]\t\t\t  ... KAMARI ....\n"+
                "[-] Welcome to KAMARI ... ##################################### initializing [-]\n");

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

        game.init(new Pack(),true);
        draw(false);
        takeTurn();

    }



}
