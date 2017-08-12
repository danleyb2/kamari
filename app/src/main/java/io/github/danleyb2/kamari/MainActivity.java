package io.github.danleyb2.kamari;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.danleyb2.kamari.core.Game;
import io.github.danleyb2.kamari.core.Player;
import io.github.danleyb2.kamari.core.PlayerType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gamePlay();
    }

    void gamePlay() {
        Game game = new Game();

        game.addPlayer("danleyb2");
        game.addPlayer(new Player("COMP", PlayerType.CPU));

        game.init();

        game.pickStarter();
    }

}
