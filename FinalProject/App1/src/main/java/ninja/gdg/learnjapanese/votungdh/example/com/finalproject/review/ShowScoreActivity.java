package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.review;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.MainActivity;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R;

/**
 * Created by VoTungDH on 17/04/24.
 */

public class ShowScoreActivity extends AppCompatActivity{
    TextView showscoretv;
    Button scorebtn;
    int score;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_score);

        showscoretv = (TextView) findViewById(R.id.highscoretv);
        scorebtn = (Button) findViewById(R.id.highscorebtn);

        Intent in = getIntent();
        score = in.getIntExtra("YourScore",0);
        showscoretv.setText(Integer.toString(score));
    }

    public void onBackPresent(){
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
        finish();
    }

    public void onClickReview(View view){
        switch (view.getId()){
            case R.id.highscorebtn:
                onBackPresent();
                break;
        }
    }
}
