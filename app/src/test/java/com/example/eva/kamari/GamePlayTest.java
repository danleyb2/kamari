package com.example.eva.kamari;

import com.example.eva.kamari.core.Card;
import com.example.eva.kamari.core.Pack;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GamePlayTest {

    @Test
    public void dealing_returns_the_pack_top() throws Exception{
        Pack pack = new Pack();
        Card topCardPreview = pack.preview();
        Card topCard = pack.deal();

        assertEquals(topCardPreview,topCard);
    }


    @Test
    public void the_pack_is_shuffled_randomly() throws Exception {
        Pack pack = new Pack();
        // TODO: 10/15/17 Preview top card
        Card topCardPreview = pack.preview();
        pack.shuffle();
        Card topCard = pack.deal();
        assertNotEquals(topCard,topCardPreview);

        pack = new Pack();
        // TODO: 10/15/17 Preview top card
        Card topCard2Preview = pack.preview();
        pack.shuffle();
        Card topCard2 = pack.deal();
        assertNotEquals(topCard2,topCard2Preview);

        assertNotEquals(topCard,topCard2);

    }
}