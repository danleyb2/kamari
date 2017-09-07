package com.example.eva.kamari.core;


import android.util.Log;
import android.widget.Switch;

import com.example.eva.kamari.UserAction;

import java.util.ArrayList;

public class Player {
    private static final String TAG = Player.class.getSimpleName();
    private String name;
    private ArrayList<Card> hand = new ArrayList<>(52);
    // private int numCards = -1;
    private PlayerType type;
    private boolean isOpponent = true;

    private UserAction userAction;

    public Player(String playerName, PlayerType type, boolean isOpponent) {
        this.name = playerName;
        this.type = type;
        this.isOpponent = isOpponent;
    }

    public String getName() {
        return name;
    }

    public int getNumCards() {
        return hand.size();
    }

    public void give(Card card) {
        //this.numCards++;
        if (isOpponent) {
            this.hand.add(card);
        } else {
            this.hand.add(new MyCard(card));
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public boolean isOpponent() {
        return isOpponent;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void playTurn(Game game) {
        Log.i(TAG,"playTurn");

        Card facing = game.getJustPlayed();
        Log.i(TAG,"previous played card is "+facing);

        // get possible card requirements and process the actions
        int action = facing.getAction();
        Log.i(TAG,"Action is : "+String.valueOf(action));

        Rank rankReq = null;
        Suit suitReq = null;

        switch (action){
            case 0:// e.g 2,3 gives
                switch (facing.getRank()){
                    case Ace:
                        if (facing.getSuit()==Suit.Spades){
                            /**
                             * An A of spades (the congress)-
                             * It is a powerful player.
                             * When dropped, can ask for any specific card.
                             * For example, it can ask for a 7 of clubs.
                             * It can be stopped by dropping an A (clubs, diamonds or hearts)
                             * and the search reduces to a character or symbol.
                             * i.e. it becomes either a 7 or clubs.
                             */

                            Card card =  this.requestCard(false);

                            //game.requestCard();


                        }else {
                            /**
                             * An A of clubs, diamonds or hearts
                             * - This can ask for a specific symbol regardless of the top dropped card.
                             * It is known as the “game changer” since
                             * it can switch the flow of symbols being dropped to the one asked.
                             */
                            Card card =  this.requestCard(true);
                        }
                        break;
                    case Two:
                        Log.i(TAG,"player should pick 2 cards unless has A");
                        break;
                    case Three:
                        Log.i(TAG,"player should pick 3 cards unless has A");
                        break;
                }

                break;

            case 1:// takes

                rankReq = facing.getRank();
                suitReq = facing.getSuit();

                switch(facing.getRank()){
                    case Queen:
                       Card requested =  game.requestedCard();
                        rankReq = requested.getRank();
                        suitReq = requested.getSuit();
                        break;
                    case Jack:
                        game.jump();
                        break;
                    case King:
                        game.kickBack();
                        break;

                }

                facing.getValidDeal();

                //  pick
                facing.getCantDealAction();



                break;

            case 2:// Kickback
                break;

            case 3:// Jump

        }

        Log.i(TAG,"Rank Request: "+rankReq+", Suit Request: "+suitReq);



        ArrayList<Card> play = new ArrayList<>();
        for (Card card : getHand()) {
            if (card.getRank() == rankReq||card.getSuit()==suitReq) {
                play.add(card);


                break;
            }
        }



        if (play.isEmpty()) {
            Log.i(TAG,"Player decided to pick");
            give(game.getPack().deal());
        } else {
            Log.i(TAG,"Player decided to deal ");
            getHand().removeAll(play);
            game.playTurn(play, this);
        }

    }

    private Card requestCard(boolean onlySymbols) {
        if (isOpponent()){
            return new Card(Rank.Seven,Suit.Clubs);
        }else {
            return userAction.onRequestCard();

        }


    }

    public void setOnRequestCallback(UserAction userAction) {
        this.userAction = userAction;
    }
}
