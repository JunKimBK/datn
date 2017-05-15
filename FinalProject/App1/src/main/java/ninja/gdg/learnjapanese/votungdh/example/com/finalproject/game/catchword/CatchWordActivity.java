package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.game.catchword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R;
/**
 * Created by VoTungDH on 17/03/31.
 */
public class CatchWordActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catch_word);

        findViewById(R.id.btn_play).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this,PlayActivity.class));
    }
}
