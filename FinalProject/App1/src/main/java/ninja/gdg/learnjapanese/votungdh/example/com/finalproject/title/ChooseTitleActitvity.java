package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.title;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.Callable;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.OkhttpRequest;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.model.Title;

/**
 * Created by VoTungDH on 23/03/2017.
 */
public class ChooseTitleActitvity extends AppCompatActivity {

    ListView lvTitle;
    ArrayList<String> arrID, arrTitle, arrMeaning, arrPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_title_layout);

        lvTitle = (ListView) findViewById(R.id.lvTitle);

        try {
            Display(lvTitle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Display(final ListView lv) throws IOException {
        arrID = new ArrayList<>();
        arrTitle = new ArrayList<>();
        arrMeaning = new ArrayList<>();
        arrPicture = new ArrayList<>();

        OkhttpRequest okhttpRequest = new OkhttpRequest();
        okhttpRequest.getjacksonTitle(new Callable<List<Title>>() {
            @Override
            public void next(final List<Title> titles) {
            for (Title title : titles) {
                arrID.add(String.valueOf(title.getIdtitle()));
                arrTitle.add(title.getTitle());
                arrPicture.add(title.getPath());
                arrMeaning.add(title.getMeaning());
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lv.setAdapter(new TitleAdapter(ChooseTitleActitvity.this ,titles));
                }
            });
            }
        });
    }
}