package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.Callable;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.OkhttpRequest;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.model.Word;

/**
 * Created by VoTungDH on 17/03/30.
 */

public class FlashcardMainActivity extends Activity {
    ArrayList<String> arrWord;
    TextView titlequestion, scoretv;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcard_question_layout);

        scoretv = (TextView) findViewById(R.id.score_id);
        titlequestion = (TextView) findViewById(R.id.titleQuestion);

        Intent in = getIntent();
        score = in.getIntExtra("UpdateScore",0);
        scoretv.setText(Integer.toString(score));

        try {
            GetTitleQuestion(titlequestion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GetTitleQuestion(final TextView tvName) throws IOException{
        arrWord = new ArrayList<>();
        OkhttpRequest okhttpRequest = new OkhttpRequest();
        okhttpRequest.getjacksonWord(new Callable<List<Word>>() {
            @Override
            public void next(final List<Word> words) {
                for (Word word : words) {
                    arrWord.add(word.getWord());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Random rd = new Random();
                        int x = rd.nextInt(arrWord.size());
                        tvName.setText(arrWord.get(x));
                    }
                });
            }
        });
    }

    public void StartGameFlashCard(View view) {
        Bundle bundle;
        Intent in;
        switch (view.getId()) {
            case R.id.btnStart:
                bundle = new Bundle();
                bundle.putString("Title", titlequestion.getText().toString());
                in = new Intent(view.getContext(), ChooseCorrectPictureActivity.class);
                in.putExtra("Data1", bundle);
                startActivity(in);
                finish();
                break;
            case R.id.highscore_btn:
                in = new Intent(this, HighScoreActivity.class);
                in.putExtra("HighScore",score);
                startActivity(in);
                finish();
                break;
        }
    }
}
