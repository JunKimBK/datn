package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.listening;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.Callable;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.Config;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.OkhttpRequest;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.model.Word;


/**
 * Created by VoTungDH on 06/03/2017.
 */
public class ListeningActivity extends Activity {

    TextView tvName, tvBack;
    ImageView imgFront;
    ImageButton btSound, btnPre, btnNext;
    ArrayList<String> arrWord, arrMeaning, arrSound, arrPicture;
    ArrayList<Integer> arrIDTitle;
    MediaPlayer mediaPlayer;
    RelativeLayout rltFlash, rltBackCard;
    int run = 0;
    AnimatorSet setRightOut, setLeftIn;
    boolean isBackVisible = false;
    int intID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listening_layout);

        Intent in = getIntent();
        Bundle bundle = in.getBundleExtra("Data");
        intID = bundle.getInt("ID");

        tvName = (TextView) findViewById(R.id.tvNamePic);
        imgFront = (ImageView) findViewById(R.id.imgFrontRv);
        tvBack = (TextView) findViewById(R.id.imgBack);
        btnPre = (ImageButton) findViewById(R.id.btPrev);
        btnNext = (ImageButton) findViewById(R.id.btNext);
        rltFlash = (RelativeLayout) findViewById(R.id.relatFlashRv);
        rltBackCard = (RelativeLayout) findViewById(R.id.rltBack);
        btSound = (ImageButton) findViewById(R.id.btSound);

        setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.anim.flash_right_in);
        setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.anim.flash_left_in);

        rltFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlipCard();
            }
        });

        try {
            Display(imgFront);
        } catch (IOException e) {
            DisableControl();
            e.printStackTrace();
        }

        tvBack.setGravity(Gravity.CENTER);
        tvBack.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
    }

    public void Display(final ImageView imgView) throws IOException{

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
                        tvName.setText(arrWord.get(0));
                        tvBack.setText(arrMeaning.get(0));
                        tvBack.setGravity(Gravity.CENTER);
                        tvBack.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                        tvName.setGravity(Gravity.CENTER);
                        Picasso.with(ListeningActivity.this).load(Config.HOST_PATH_WORD + arrPicture.get(0)).into(imgView);
                    }
                });
            }
        });
    }

    public void FlipCard() {
        if (!isBackVisible) {
            setRightOut.setTarget(imgFront);
            setLeftIn.setTarget(rltBackCard);
            setRightOut.start();
            setLeftIn.start();
            tvName.setVisibility(View.INVISIBLE);
            isBackVisible = true;
            try {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(Config.HOST_SOUND+arrSound.get(run));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            rltBackCard.setVisibility(View.VISIBLE);
        } else {
            setRightOut.setTarget(rltBackCard);
            setLeftIn.setTarget(imgFront);
            setRightOut.start();
            setLeftIn.start();
            tvName.setVisibility(View.VISIBLE);
            isBackVisible = false;
            try {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(Config.HOST_SOUND+arrSound.get(run));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            rltBackCard.setVisibility(View.VISIBLE);
        }

    }

    public void DisableControl() {
        tvName.setVisibility(View.INVISIBLE);
        imgFront.setVisibility(View.INVISIBLE);
        tvBack.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        btnPre.setVisibility(View.INVISIBLE);
    }

    public void Next() {
        if (run < (arrWord.size() - 1)) {
            run++;
            Picasso.with(ListeningActivity.this).load(Config.HOST_PATH_WORD+arrPicture.get(run)).into(imgFront);
            tvName.setText(arrWord.get(run));
            tvBack.setText(arrMeaning.get(run));
            tvName.setGravity(Gravity.CENTER);
            if (isBackVisible) {
                setLeftIn.setTarget(imgFront);
                setLeftIn.start();
                rltBackCard.setVisibility(View.INVISIBLE);
                isBackVisible = !isBackVisible;
                tvName.setVisibility(View.VISIBLE);
            }
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Message");
            alert.setMessage("You finished lesson!!!Do you want to review it?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    run = 0;
                    Picasso.with(ListeningActivity.this).load(Config.HOST_PATH_WORD+arrPicture.get(run)).into(imgFront);
                    tvName.setText(arrWord.get(run));
                    tvBack.setText(arrMeaning.get(run));
                    tvName.setGravity(Gravity.CENTER);
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   onBackPressed();
                }
            });
            alert.create().show();
        }
    }

    public void Previous() throws IOException{
        if (run > 0) {
            run--;
            Picasso.with(ListeningActivity.this).load(Config.HOST_PATH_WORD+arrPicture.get(run)).into(imgFront);
            tvName.setText(arrWord.get(run));
            tvBack.setText(arrMeaning.get(run));
            tvName.setGravity(Gravity.CENTER);
        }
    }

    public void onSound() throws IOException{
        Boolean mpPlayingStatus = false;
        mediaPlayer = new MediaPlayer();
        if (mpPlayingStatus == true) {
            mediaPlayer.stop();
            mediaPlayer.release();
        } else {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(Config.HOST_SOUND+arrSound.get(run));
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
    }

    public void onClickControl(View view) throws IOException {
        switch (view.getId()) {
            case R.id.btSound:
                onSound();
                break;
            case R.id.btNext:
                Next();
                break;
            case R.id.btPrev:
                Previous();
                break;
        }
    }
}
