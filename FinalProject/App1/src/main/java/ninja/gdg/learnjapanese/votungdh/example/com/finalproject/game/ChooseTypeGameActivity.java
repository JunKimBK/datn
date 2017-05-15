package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.game.catchword.CatchWordActivity;

/**
 * Created by VoTungDH on 17/03/30.
 */

public class ChooseTypeGameActivity extends Activity {
    String strID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_type_game);

        Intent in = getIntent();
        Bundle bundle = in.getBundleExtra("Data");
    }

    public void ChooseTypeGame(View view){
        Bundle bundle;
        Intent in;
        switch (view.getId()){
            case R.id.btnFlashCard:
                bundle = new Bundle();
                bundle.putString("Type", strID);
                in = new Intent(view.getContext(),FlashcardMainActivity.class);
                in.putExtra("Data",bundle);
                startActivity(in);
                finish();
                break;
            case R.id.btnCatchWord:
                in = new Intent(this, CatchWordActivity.class);
                startActivity(in);
                finish();
                break;
            case R.id.btnDrawWord:
                in = new Intent(this, DrawWordActivity.class);
                startActivity(in);
                finish();
                break;
        }
    }
}