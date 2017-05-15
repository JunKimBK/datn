package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.game;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.model.Word;

import static ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R.id.titleQuestion;
import static ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R.raw.i;
import static ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R.raw.re;

/**
 * Created by VoTungDH on 17/03/31.
 */

public class ChooseCorrectPictureActivity extends Activity{
    private ProgressBar mProgressBar;
    private int mProgressStatus = 6;
    private static int score = 0, count = 0;
    private ImageView imageView1, imageView2, imageView3, imageView4;
    ArrayList<String> arrPicture, arrWord, arrWord1, arrWord2, arrWord3,arrPicture2;
    private String answer2, titlequestion;
    TextView tvMeaning, tv1, tv2, tv3, tv4, time;
    boolean isBackVisible = false;
    ViewGroup rltFlash1,rltFlash2, rltFlash3, rltFlash4;
    AnimatorSet setRightOut, setLeftIn;

    private Handler mHandler = new Handler();
    private boolean canRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcard_layout);

        progressBar();

        Intent in = getIntent();
        Bundle bundle = in.getBundleExtra("Data1");
        titlequestion = bundle.getString("Title");

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);

        tvMeaning = (TextView) findViewById(titleQuestion);
        time = (TextView) findViewById(R.id.time);

        rltFlash1 = (ViewGroup) findViewById(R.id.rltFlash1);
        rltFlash2 = (ViewGroup) findViewById(R.id.rltFlash2);
        rltFlash3 = (ViewGroup) findViewById(R.id.rltFlash3);
        rltFlash4 = (ViewGroup) findViewById(R.id.rltFlash4);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.anim.flash_right_in);
        setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.anim.flash_left_in);

        try {
            queryDb();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queryDb() throws IOException {
        GetWordFalse();
    }

    public void progressBar(){
        new Thread(new Runnable() {
            public void run() {
            while (mProgressStatus > 0 && canRun) {
                mHandler.post(new Runnable() {
                    public void run() {
                    mProgressBar.setProgress(mProgressStatus);
                        if(mProgressStatus == 0){
                            NotificationLostGame();
                        }
                    }
                });
                mProgressStatus--;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time.setText(String.valueOf(mProgressStatus));
                    }
                });
                android.os.SystemClock.sleep(1000);
            }
            }
        }).start();
    }

    public void GetWordTrue(final TextView tvName) throws IOException {
        arrWord1 = new ArrayList<>();

        OkhttpRequest okhttpRequest = new OkhttpRequest();
        okhttpRequest.getjacksonWord(new Callable<List<Word>>() {
            @Override
            public void next(final List<Word> words) {
            for (Word word : words) {
                arrWord1.add(word.getWord());
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                tvName.setText(titlequestion);
                }
            });
            }
        });
    }

    private static int[] rand3(int max) {
        Random rand = new Random(new Date().getTime());
        int[] result = new int[3];
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

    public void GetWordFalse() throws IOException {
        arrWord2 = new ArrayList<>();

        OkhttpRequest okhttpRequest = new OkhttpRequest();
        okhttpRequest.getjacksonWord(new Callable<List<Word>>() {
            @Override
            public void next(final List<Word> words) {
                for (Word word : words) {
                    arrWord2.add(word.getWord());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tvArr[] = {tv1,tv2,tv3,tv4};
                        ImageView ivArr[] = {imageView1, imageView2, imageView3, imageView4};
                        Random rnd = new Random();
                        int x = rnd.nextInt(tvArr.length);
                        try {
                            GetWordTrue(tvArr[x]);
                            GetImageView(ivArr[x],titlequestion);
                            arrWord2.remove(titlequestion);
                            int[] result = rand3(arrWord2.size());
                            for (int i = 0; i < result.length; i++) {
                                if(x!=i) {
                                    System.out.println(result[i]);
                                    tvArr[i].setText(arrWord2.get(result[i]));
                                    try {
                                        GetImageViewFalse(ivArr[i], tvArr[i]);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    public void GetImageViewFalse(final ImageView imgView, final TextView tvName) throws IOException {
        arrPicture2 = new ArrayList<>();
        arrWord3 = new ArrayList<>();

        OkhttpRequest okhttpRequest = new OkhttpRequest();
        okhttpRequest.getjacksonWord(new Callable<List<Word>>() {
            @Override
            public void next(final List<Word> words) {
                for (Word word : words) {
                    arrWord3.add(word.getWord());
                    arrPicture2.add(word.getPicture());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    for(int i = 0; i < arrPicture2.size();i++){
                        if(arrWord3.get(i).equals(tvName.getText().toString())){
                            Picasso.with(ChooseCorrectPictureActivity.this).load(Config.HOST_PATH_WORD+arrPicture2.get(i)).into(imgView);
                        }
                    }
                    }
                });
            }
        });
    }

    public void GetImageView(final ImageView imgView, final String tvName) throws IOException {
        arrPicture = new ArrayList<>();
        arrWord = new ArrayList<>();

        OkhttpRequest okhttpRequest = new OkhttpRequest();
        okhttpRequest.getjacksonWord(new Callable<List<Word>>() {
            @Override
            public void next(final List<Word> words) {
                for (Word word : words) {
                    arrWord.add(word.getWord());
                    arrPicture.add(word.getPicture());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    for(int i = 0; i<arrPicture.size();i++){
                        if(arrWord.get(i).equals(tvName)){
                            Picasso.with(ChooseCorrectPictureActivity.this).load(Config.HOST_PATH_WORD+arrPicture.get(i)).into(imgView);
                        }
                    }
                    }
                });
            }
        });
    }

    public void NotificationLostGame(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("You lost!!!!");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BackGame();
            }
        });
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }

    public void NotificationWinGame(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("You win!!!!");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NextLevel();
            }
        });
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }

    public void FlipCard(ImageView img, ViewGroup rlt) {
        if (!isBackVisible) {
            setRightOut.setTarget(img);
            setLeftIn.setTarget(rlt);
            setRightOut.start();
            setLeftIn.start();
            isBackVisible = true;
            rlt.setVisibility(View.VISIBLE);
        } else {
            setRightOut.setTarget(rlt);
            setLeftIn.setTarget(img);
            setRightOut.start();
            setLeftIn.start();
            isBackVisible = false;
            rlt.setVisibility(View.VISIBLE);
        }
    }

    public void CheckChooseAnswer1() {
        canRun = false;
        if (imageView1.isClickable()) {
            answer2 = tv1.getText().toString();
            tv1.setVisibility(View.VISIBLE);
            FlipCard(imageView1, rltFlash1);
            if (answer2.equals(titlequestion)) {
                NotificationWinGame();
                score++;
                Toast.makeText(ChooseCorrectPictureActivity.this, "You are smart!", Toast.LENGTH_SHORT).show();
            } else {
                NotificationLostGame();
                score = 0;
                Toast.makeText(ChooseCorrectPictureActivity.this, "You are wrong! Please, try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void CheckChooseAnswer2() {
        canRun = false;
        if (imageView2.isClickable()) {
            answer2 = tv2.getText().toString();
            tv2.setVisibility(View.VISIBLE);
            FlipCard(imageView2, rltFlash2);
            if (answer2.equals(titlequestion)) {
                NotificationWinGame();
                score++;
                Toast.makeText(ChooseCorrectPictureActivity.this, "You are smart!", Toast.LENGTH_SHORT).show();
            } else {
                NotificationLostGame();
                score = 0;
                Toast.makeText(ChooseCorrectPictureActivity.this, "You are wrong! Please, try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void CheckChooseAnswer3() {
        canRun = false;
        if (imageView3.isClickable()) {
            FlipCard(imageView3, rltFlash3);
            tv3.setVisibility(View.VISIBLE);
            answer2 = tv3.getText().toString();
            if (answer2.equals(titlequestion)) {
                NotificationWinGame();
                score++;
                Toast.makeText(ChooseCorrectPictureActivity.this, "You are smart!", Toast.LENGTH_SHORT).show();
            } else {
                NotificationLostGame();
                score = 0;
                Toast.makeText(ChooseCorrectPictureActivity.this, "You are wrong! Please, try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void CheckChooseAnswer4() {
        canRun = false;
        if (imageView4.isClickable()) {
            answer2 = tv4.getText().toString();
            tv4.setVisibility(View.VISIBLE);
            FlipCard(imageView4, rltFlash4);
            if (answer2.equals(titlequestion)) {
                NotificationWinGame();
                score++;
                Toast.makeText(ChooseCorrectPictureActivity.this, "You are smart!", Toast.LENGTH_SHORT).show();
            } else {
                NotificationLostGame();
                score = 0;
                Toast.makeText(ChooseCorrectPictureActivity.this, "You are wrong! Please, try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClickView(View view){
        switch (view.getId()){
            case R.id.imageView1:
                CheckChooseAnswer1();
                break;
            case R.id.imageView2:
                CheckChooseAnswer2();
                break;
            case R.id.imageView3:
                CheckChooseAnswer3();
                break;
            case R.id.imageView4:
                CheckChooseAnswer4();
                break;
        }
    }

    public void NextLevel(){
        Intent intent = new Intent(this,FlashcardMainActivity.class);
        intent.putExtra("UpdateScore",score);
        startActivity(intent);
        finish();
    }

    public void BackGame(){
        Intent intent = new Intent(this, ChooseTypeGameActivity.class);
        startActivity(intent);
        finish();
    }
}
