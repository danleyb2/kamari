package com.example.eva.kamari.core;

import android.util.Log;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * Created by danleyb2 on 8/12/17.
 */

public class Game {

    public interface PlayCb{
        void onDraw(Game game);
        void playerTurn(Game game);
    }

    private static final String TAG = Game.class.getSimpleName();

    private ArrayList<Player> players = new ArrayList<>();
    private Pack pack;

    private ArrayList<Play> played = new ArrayList<>();

    private int activeTurn = 0;
    private int direction = 1;
    private Card requestedCard = null;

    public void init(Pack pack,boolean shuffle) {
        this.pack = pack;

        if (shuffle) {
            Logger.i("Shuffling pack ");
            this.pack.shuffle();
        }

        Logger.i("Assigning initial cards to "+players.size()+" players");
        for (Player player :
                players) {
            Logger.i("Player "+String.valueOf(player));
            for (int i = 0; i < 3; i++) {
                player.give(pack.deal());
            }
            Logger.i(player.dumpString());
        }

        //TODO 1. The starting card can be any except a A, 2, 3, 8, J, Q, K or a joker

        Card cardStarting = pack.deal();
        Logger.i("Starting card :"+cardStarting);
        while (cardStarting.getRank() == Rank.Ace ||
                cardStarting.getRank() == Rank.Two||
                cardStarting.getRank() == Rank.Three ||
                cardStarting.getRank() == Rank.Eight ||
                cardStarting.getRank() == Rank.Jack ||
                cardStarting.getRank() == Rank.Queen ||
                cardStarting.getRank() == Rank.King ) {


            Logger.i("!! Invalid Starting card :"+cardStarting);
            Logger.i("Shifting pack and adding to bottom :");
            pack.addToBottom(cardStarting);

            cardStarting = pack.deal();
            Logger.i( "New Starting card :" + cardStarting);
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
        if (Math.abs(this.activeTurn) == players.size()) // forward don't pass the players count
            this.activeTurn = 0;
        else if (this.activeTurn<0){ // reverse check for -ve index
            this.activeTurn = players.size() - 1;
        }

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

        // loop up to last give or start
        for(int j = played.size() - 1; j >= 0; j--){
            Play play;
            play = played.get(j);
            if(play.getPlayAction() == PlayAction.GIVE || play.getPlayAction() == PlayAction.START)
            return play.getLastCard();
        }

        return null;

    }

    public Play getLastPlay() {

        // loop up to last give or start
        for(int j = played.size() - 1; j >= 0; j--){
            Play play;
            play = played.get(j);
            if(play.getPlayAction() == PlayAction.GIVE || play.getPlayAction() == PlayAction.START)
                return play;
        }

        return null;

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

        if (played.getRank() == Rank.King   ){
            return true;
        }



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

    public void takeTurn(PlayCb playCb){
        playCb.onDraw(this);

        Player aP = this.turn();
        Play lastPlay = this.getLastPlay();
        Card jP = lastPlay.getLastCard();

        Log.e(TAG, "[-] Take turn *********************[" + aP.getName() + "]**********************[-]");

        // pre-process previous play actions,
        // 1. pick cards
        if (jP.getRank() == Rank.Two && lastPlay.getPlayAction() == PlayAction.GIVE) {
            ArrayList<Card> play = new ArrayList<>(2);
            Card card;

            card = this.getPack().deal();
            play.add(0,card);
            aP.give(card);

            card = this.getPack().deal();
            play.add(1,card);
            aP.give(card);

            //todo add picked cards to Play
            this.getPlayed().add(new Play(play,aP, PlayAction.TAKE));

            this.takeTurn(playCb);

        }

        if (jP.getRank() == Rank.Three && lastPlay.getPlayAction() == PlayAction.GIVE) {

            ArrayList<Card> play = new ArrayList<>(3);
            Card card;

            card = this.getPack().deal();
            play.add(0,card);
            aP.give(card);

            card = this.getPack().deal();
            play.add(1,card);
            aP.give(card);

            card = this.getPack().deal();
            play.add(2,card);
            aP.give(card);

            //todo add picked cards to Play
            this.getPlayed().add(new Play(play,aP, PlayAction.TAKE));
            takeTurn(playCb);
        }

        // kick back
        if (lastPlay.getPlayAction() == PlayAction.GIVE && /* last play was an addition to deck*/
                jP.getRank() == Rank.King &&  /* of kick back card */
                lastPlay.getPlayer() != aP  /* not played by active turn */ ){
            this.kickBack();
            takeTurn(playCb);
        }

        // Jump
        if (lastPlay.getPlayAction() == PlayAction.GIVE &&
                jP.getRank() == Rank.Jack &&
                lastPlay.getPlayer() != aP  /* not played by active turn */ ){
            this.jump();
            takeTurn(playCb);
        }


        if (aP.isOpponent()) {
            Log.i(TAG,"player is Opponent, getting AI play");
            aP.playTurn(this);
            takeTurn(playCb);

        } else {
            // waiting for me to take turn
            playCb.playerTurn(this);
        }

    }
}
