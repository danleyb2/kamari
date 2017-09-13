package com.example.eva.kamari;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.eva.kamari.core.Card;
import com.example.eva.kamari.core.MyCard;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageViewCard;
    private CardView cardView;

    private Card card;
    OnItemSelectedListener itemSelectedListener;

    public CardView getCardView() {
        return cardView;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public MyViewHolder(View view, OnItemSelectedListener listener) {
        super(view);
        itemSelectedListener = listener;

        imageViewCard = (ImageView) view.findViewById(R.id.imageViewCard);
        cardView = (CardView) view.findViewById(R.id.card);
        imageViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCard mCard = (MyCard) card;

                if (mCard.isSelected()) {
                    mCard.setSelected(false);
                    //cardView.setCardBackgroundColor(Color.WHITE);
                } else {
                    mCard.setSelected(true);
                    //cardView.setCardBackgroundColor(Color.GREEN);
                }
                itemSelectedListener.onItemSelected(mCard);

            }
        });


    }

    public interface OnItemSelectedListener {

        void onItemSelected(MyCard myCard);
    }
}
