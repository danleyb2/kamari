package com.example.eva.kamari;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eva.kamari.core.Card;
import com.example.eva.kamari.core.Player;


/**
 * Created by danleyb2 on 9/3/17.
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.MyViewHolder> {

    private Card[] cardsList;
    private Context context;
    private Player player;

    public CardsAdapter(SinglePlayer context, Player player) {
        this(context, player.getHand());
        this.player = player;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewCard;

        public MyViewHolder(View view) {
            super(view);
            imageViewCard = (ImageView) view.findViewById(R.id.imageViewCard);


        }
    }


    public CardsAdapter(Context context, Card[] cardsList) {
        this.context = context;
        this.cardsList = cardsList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hand_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Card card = cardsList[position];
        holder.imageViewCard.setImageDrawable(Utils.loadDrawable(context, card.getDrawableImage()));

    }

    @Override
    public int getItemCount() {
        return player.getNumCards();
    }
}
