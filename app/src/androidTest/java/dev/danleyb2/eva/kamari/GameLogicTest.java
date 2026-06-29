package dev.danleyb2.eva.kamari;

import android.support.test.runner.AndroidJUnit4;

import dev.danleyb2.kamari.core.Game;
import dev.danleyb2.kamari.core.Pack;
import dev.danleyb2.kamari.core.Player;
import dev.danleyb2.kamari.core.PlayerType;
import dev.danleyb2.kamari.core.Rank;
import dev.danleyb2.kamari.core.Suit;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GameLogicTest {

    @Test
    public void packContains54Cards() throws Exception {

        Pack pack = new Pack();
        assertEquals(true, pack.getDeck().length == 52);

    }
    @Test
    public void packIsShuffledRandomly() throws Exception {

        // Pack packNotShuffled = new Pack();
        Pack pack1 = new Pack();
        pack1.shuffle();

        // Pack packNotShuffled2 = new Pack();
        Pack pack2 = new Pack();
        pack2.shuffle();

        int match = 0;

        for (int i= 0;i<pack1.getDeck().length; i++) {
            if(pack1.getDeck()[i] == pack2.getDeck()[i]){
                match=+1;
            }

        }

        assertEquals(true, match<5);

    }

    @Test
    public void kickBack() throws Exception{

        // 3,3,1,

        Game  game = new Game();
        // PLAYER 1
        Player me = game.addPlayer("danleyb2", false);
        // PLAYER 2
        game.addPlayer(new Player("COMP", PlayerType.CPU, true));
        Pack pack = new Pack();

        // set starting card
        pack.moveCard(Rank.Four,Suit.Clubs,6);
        // set next card
        pack.moveCard(Rank.King,Suit.Clubs,1);

        game.init(pack,false);
        // P1 PLAY K
        game.takeTurn(new Game.PlayCb() {
            @Override
            public void onDraw(Game game) {
                System.out.println(game);
            }

            @Override
            public void playerTurn(Game game) {

            }
        });

        // GAME DIRECTION CHANGED

    }

    @Test
    public void question() throws Exception{
        // PLAYER 1
        // PLAYER 2

        // P1 PLAY Q

        // Game Expect question from p1 to p2
    }

    @Test
    public void pick2() throws Exception{
        // PLAYER 1
        // PLAYER 2

        // P1 PLAY 2

        // Game give 2 cards to p2
    }


}
