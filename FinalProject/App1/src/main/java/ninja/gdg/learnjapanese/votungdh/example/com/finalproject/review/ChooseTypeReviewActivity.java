package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.review;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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
public class ChooseTypeReviewActivity extends AppCompatActivity {

    ArrayList<String> arrID, arrTitle;
    TextView tvTitleName;
    int intID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_type_review_layout);

        Intent in = getIntent();
        Bundle bundle = in.getBundleExtra("Data");
        intID = bundle.getInt("ID");

        tvTitleName = (TextView) findViewById(R.id.titleQuestion);

        try {
            GetTitleName(tvTitleName);
            //, "SELECT Title FROM Title WHERE IDtitle=" + strID
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void ChooseTypeReviewing(View view) {
        Bundle bundle;
        Intent in;
        switch (view.getId()) {
            case R.id.btnStart:
                bundle = new Bundle();
                bundle.putInt("Type", intID);
                in = new Intent(view.getContext(), ReviewActivity.class);
                in.putExtra("Data", bundle);
                startActivity(in);
        }
    }
}
