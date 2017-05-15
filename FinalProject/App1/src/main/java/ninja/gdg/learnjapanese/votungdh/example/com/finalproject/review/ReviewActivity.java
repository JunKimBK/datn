package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.review;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.Callable;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.Config;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.OkhttpRequest;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.model.Title;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.model.Word;

import static ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R.raw.a;
import static ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R.raw.i;

/**
 * Created by VoTungDH on 23/03/2017
 */

public class ReviewActivity extends AppCompatActivity {
    TextView tvTitle, tvBack, time_review;
    ImageView imgFront;
    RadioButton rbtAnswer1, rbtAnswer2, rbtAnswer3;
    ArrayList<String> arrWord, arrMeaning, arrSound, arrID, arrTitle, arrPicture;
    ArrayList<Integer> arrIDTitle;
    int count = 0, run = 0, score = 0, intID, trueAnswer;
    String Answer;
    AnimatorSet setRightOut, setLeftIn;
    boolean isBackVisible = false, canRun = true;
    RelativeLayout rltFlash, rltBackCard;
    private ProgressBar mProgressBar;
    private int mProgressStatus = 30;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_layout);

        Intent in = getIntent();
        Bundle bundle = in.getBundleExtra("Data");
        intID = bundle.getInt("Type");

        imgFront = (ImageView) findViewById(R.id.imgFrontRv);
        tvTitle = (TextView) findViewById(R.id.tvTitleRv);
        time_review = (TextView) findViewById(R.id.time_review);
        rbtAnswer1 = (RadioButton) findViewById(R.id.rbAnswer1);
        rbtAnswer2 = (RadioButton) findViewById(R.id.rbAnswer2);
        rbtAnswer3 = (RadioButton) findViewById(R.id.rbAnswer3);
        rltFlash = (RelativeLayout) findViewById(R.id.relatFlashRv);
        rltBackCard = (RelativeLayout) findViewById(R.id.rltBack);
        tvBack = (TextView) findViewById(R.id.imgBack);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
        setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.anim.flash_right_in);
        setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.anim.flash_left_in);

        progressBar();

        try {
            GetTitleName(tvTitle);
            ShowFirstQuestion(imgFront);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void progressBar(){
        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
            while (mProgressStatus > 0 && canRun) {
                // Update the progress bar
                mHandler.post(new Runnable() {
                    public void run() {
                        mProgressBar.setProgress(mProgressStatus);
                        if(mProgressStatus == 0){
                            onBackPresent();
                        }
                    }
                });
                mProgressStatus--;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time_review.setText(String.valueOf(mProgressStatus));
                    }
                });
                android.os.SystemClock.sleep(1000);
            }
            }
        }).start();
    }

    public void GetTitleName(final TextView tvName) throws IOException{
        arrID = new ArrayList<>();
        arrTitle = new ArrayList<>();

        OkhttpRequest okhttpRequest = new OkhttpRequest();
        okhttpRequest.getjacksonTitle(new Callable<List<Title>>() {
            @Override
            public void next(final List<Title> titles) {
            for (Title title : titles) {
                arrID.add(String.valueOf(title.getIdtitle()));
                if (title.getIdtitle()==intID) {
                    arrTitle.add(title.getTitle());
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvName.setText(arrTitle.get(0));
                }
            });
            }
        });
    }

    public void ShowFirstQuestion(final ImageView imgView) throws IOException{
        arrWord = new ArrayList<>();
        arrMeaning = new ArrayList<>();
        arrPicture = new ArrayList<>();
        arrSound = new ArrayList<>();
        arrIDTitle = new ArrayList<>();

        OkhttpRequest okhttpRequest = new OkhttpRequest();
        okhttpRequest.getjacksonWord(new Callable<List<Word>>() {
            @Override
            public void next(final List<Word> words) {
            for (Word word : words) {
                arrIDTitle.add(word.getIdtitle());
                if (word.getIdtitle()==intID) {
                    arrWord.add(word.getWord());
                    arrMeaning.add(word.getMeaning());
                    arrPicture.add(word.getPicture());
                    arrSound.add(word.getSound());
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Random rnd = new Random();
                    trueAnswer = rnd.nextInt(arrWord.size());
                    Picasso.with(ReviewActivity.this).load(Config.HOST_PATH_WORD+arrPicture.get(trueAnswer)).into(imgView);
                    Answer = arrWord.get(trueAnswer);
                    tvBack.setText(Answer);
                    ShowAnswer(Answer);
                }
            });
            }
        });
    }

    public void ShowAnswer(String ans){
        ArrayList<String> answer1 = new ArrayList<String>();
        ArrayList<String> answer2 = new ArrayList<String>();
        answer1.add(ans);
        Random rnd = new Random();
        for (int i = 0; i < arrWord.size(); i++) {
            int r = rnd.nextInt(arrWord.size());
            if (count < 2) {
                String ans1 = arrWord.get(r);
                boolean d = ans.equals(ans1);
                if(d == false){
                    answer2.add(ans1);
                    answer1.add(ans1);
                    count++;
                }
            }
        }

        RadioButton arrRbt[] = {rbtAnswer1, rbtAnswer2, rbtAnswer3};

        List<RadioButton> buttons = new ArrayList<RadioButton>();
        int x = rnd.nextInt(arrRbt.length);
        buttons.add(arrRbt[x]);
        for (int i = 0; i < arrRbt.length;i++){
            if(i!=x){
                buttons.add(arrRbt[i]);
            }
        }

       for (int i = 0; i < answer1.size(); i++) {
            buttons.get(i).setText(answer1.get(i));
        }
    }

    public void FlipCard() {
        if (!isBackVisible) {
            setRightOut.setTarget(imgFront);
            setLeftIn.setTarget(rltBackCard);
            setRightOut.start();
            setLeftIn.start();
            isBackVisible = true;
            rltBackCard.setVisibility(View.VISIBLE);
        } else {
            setRightOut.setTarget(rltBackCard);
            setLeftIn.setTarget(imgFront);
            setRightOut.start();
            setLeftIn.start();
            isBackVisible = false;
            rltBackCard.setVisibility(View.VISIBLE);
        }
    }

    private static int[] rand4(int max) {
        Random rand = new Random(new Date().getTime());
        int[] result = new int[4];
        int index = 0;
        while (index < result.length) {
            int randInt = rand.nextInt(max);
            result[index] = randInt;
            boolean exist = false;
            for (int i = index - 1; i >= 0; i--) {
                if (result[index] == result[i]) {
                    exist = true;
                    break;
                }
            }
            if (exist)
                continue;
            index++;
        }
        return result;
    }

    public void Next() throws IOException {
        String title = tvTitle.getText().toString();
        if (run < 4) {
            run++;
            Picasso.with(ReviewActivity.this).load(Config.HOST_PATH_WORD + arrPicture.get(run)).into(imgFront);
            Answer = arrWord.get(run);
            tvBack.setText(Answer);
            ShowAnswer(Answer);
            if (isBackVisible) {
                setLeftIn.setTarget(imgFront);
                setLeftIn.start();
                rltBackCard.setVisibility(View.INVISIBLE);
                isBackVisible = !isBackVisible;
            }
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Message");
            alert.setMessage("You finished lesson " + title + ". Do you want to try again?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    run = 0;
                    Picasso.with(ReviewActivity.this).load(Config.HOST_PATH_WORD + arrPicture.get(run)).into(imgFront);
                    tvBack.setText(arrMeaning.get(run));
                    Answer = arrWord.get(run);
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    canRun = false;
                    onBackPresent();
                }
            });
            alert.create().show();
        }
    }

    public void CheckChooseAnswer() throws IOException{
        String answer;
        if (rbtAnswer1.isChecked()) {
            answer = rbtAnswer1.getText().toString();
            if (answer.equals(Answer)) {
                FlipCard();
                score++;
                Next();
            }else{
                Next();
            }
        }

        if (rbtAnswer2.isChecked()) {
            answer = rbtAnswer2.getText().toString();
            if (answer.equals(Answer)) {
                FlipCard();
                score++;
                Next();
            }else{
                Next();
            }
        }

        if (rbtAnswer3.isChecked()) {
            answer = rbtAnswer3.getText().toString();
            if (answer.equals(Answer)) {
                FlipCard();
                score++;
                Next();
            }else{
                Next();
            }
        }

    }

    public void onBackPresent(){
        Intent intent = new Intent(this,ShowScoreActivity.class);
        intent.putExtra("YourScore",score);
        startActivity(intent);
        finish();
    }

    public void onCLickView(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                try {
                    CheckChooseAnswer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
