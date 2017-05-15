package ninja.gdg.learnjapanese.votungdh.example.com.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.game.ChooseTypeGameActivity;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.title.ChooseTitleActitvity;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.youtube.YoutubeMainActivity;

/**
 * Created by VoTungDH on 23/03/2017.
 */
public class MainActivity extends Activity {
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ChooseAction(View view) {
        Intent in;
        switch (view.getId()) {
            case R.id.btnListening:
                bundle = new Bundle();
                bundle.putString("Type", "listening");
                in = new Intent(view.getContext(), ChooseTitleActitvity.class);
                in.putExtra("Data", bundle);
                startActivity(in);
                break;
            case R.id.btnReview:
                bundle = new Bundle();
                bundle.putString("Type", "review");
                in = new Intent(view.getContext(), ChooseTitleActitvity.class);
                in.putExtra("Data", bundle);
                startActivity(in);
                break;
            case R.id.btnVideo:
                in = new Intent(view.getContext(), YoutubeMainActivity.class);
                startActivity(in);
                break;
            case R.id.btnLearning:
                bundle = new Bundle();
                bundle.putString("Type", "learning");
                in = new Intent(view.getContext(), ChooseTypeGameActivity.class);
                in.putExtra("Data", bundle);
                startActivity(in);
                break;
        }
    }
}
