package com.example.eva.kamari;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eva.kamari.core.Card;
import com.example.eva.kamari.core.MyCard;
import com.example.eva.kamari.core.Player;

import java.util.ArrayList;


/**
 * Created by danleyb2 on 9/3/17.
 */

public class CardsAdapter extends RecyclerView.Adapter<MyViewHolder> implements MyViewHolder.OnItemSelectedListener {

    private ArrayList<Card> cardsList;
    private Context context;
    private Player player;
    MyViewHolder.OnItemSelectedListener listener;


    public CardsAdapter(SinglePlayer context, Player player, MyViewHolder.OnItemSelectedListener listener) {


        this.context = context;
        this.cardsList = player.getHand();
        this.player = player;
        this.listener = listener;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hand_card, parent, false);

        return new MyViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Card card = cardsList.get(position);
        holder.setCard(card);
        holder.imageViewCard.setImageDrawable(Utils.loadDrawable(context, card.getDrawableImage()));

        if (((MyCard) card).isSelected()) {
            holder.getCardView().setCardBackgroundColor(Color.GREEN);
        }


    }

    @Override
    public int getItemCount() {
        return player.getNumCards();
    }

    public ArrayList<MyCard> getSelectedItems() {

        ArrayList<MyCard> selectedItems = new ArrayList<>();
        for (Card item : cardsList) {
            MyCard temp = (MyCard) item;
            if (temp.isSelected()) {
                selectedItems.add(temp);
            }
        }
        return selectedItems;
    }

    @Override
    public void onItemSelected(MyCard item) {
        listener.onItemSelected(item);
    }

}
