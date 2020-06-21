package com.iamagamedev.mybrandnewgame;

import android.app.Activity;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;

public class GameActivity extends Activity {

    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        gameView = new GameView(this, size.x, size.y);
        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause(isFinishing());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return false;
    }
}
