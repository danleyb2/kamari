package io.github.danleyb2.kamari.core;

;

public class Player {
    private String name;
    private Card[] hand = new Card[52];
    private int numCards = -1;
    private PlayerType type;

    public Player(String playerName, PlayerType type) {
        this.name = playerName;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void give(Card card) {
        this.numCards++;
        this.hand[this.numCards] = card;
    }


}
