package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R;

/**
 * Created by VoTungDH on 17/04/25.
 */

public class HighScoreActivity extends AppCompatActivity{
    TextView highscoretv;
    Button highscorebtn;
    private int highscore = 0, score;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_layout);

        highscoretv = (TextView) findViewById(R.id.highscoretv);
        highscorebtn = (Button) findViewById(R.id.highscorebtn);

        Intent in = getIntent();
        score = in.getIntExtra("HighScore",0);

        updateHighScoreIfNecessary(score);
        highScore(score);
    }

    private int getHighscore() {
        sharedpreferences = getSharedPreferences("HScore", Context.MODE_PRIVATE);
        return sharedpreferences.getInt("high-score", 0);
    }

    private void updateHighScoreIfNecessary(int score) {
        int highscore = getHighscore();
        if (highscore <= score) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("high-score", score);
            editor.commit();
        }
    }

    private void highScore(int hscore){
        sharedpreferences = getSharedPreferences("HScore", Context.MODE_PRIVATE);
        hscore = sharedpreferences.getInt("high-score",0);
        highscoretv.setText(Integer.toString(hscore));
    }

    private void BackPresent(){
        Intent in = new Intent(this, ChooseTypeGameActivity.class);
        startActivity(in);
        finish();
    }

    public void onBackPresent(View view){
        switch (view.getId()){
            case R.id.highscorebtn:
                BackPresent();
                break;
        }
    }
}
