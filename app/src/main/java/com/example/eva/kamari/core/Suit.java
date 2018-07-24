package com.example.eva.kamari.core;

/**
 * Created by danleyb2 on 8/12/17.
 */

public enum Suit {
    Clubs,
    Diamonds,
    Hearts,
    Spades;

    @Override
    public String toString() {
        // ♤,♥,♢,♧
        switch (this){
            case Clubs:return "♧";
            case Diamonds:return "♢";
            case Hearts:return "♥";
            case Spades:return "♤";
        }
        return "♤,♥,♢,♧";
    }
}
