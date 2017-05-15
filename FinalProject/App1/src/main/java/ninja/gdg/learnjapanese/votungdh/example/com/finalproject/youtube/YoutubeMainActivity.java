package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.youtube;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R;


/**
 * Created by VoTungDH on 17/03/29.
 */

public class YoutubeMainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_youtube);

        ImageButton thub = (ImageButton)findViewById(R.id.thumb);
        TextView txtTitle = (TextView)findViewById(R.id.title);
        ImageButton thub1 = (ImageButton)findViewById(R.id.thumb1);
        TextView txtTitle1 = (TextView)findViewById(R.id.title1);

        String url = "http://img.youtube.com/vi/"+Config.KEY_VIDEO+"/default.jpg"; // Đây là cách lấy ảnh icon cho video đó. Sau đó hiển thị nó vào view bằng Picasso
        String url1 = "http://img.youtube.com/vi/"+Config.KEY_VIDEO_1+"/default.jpg";
        Picasso.with(YoutubeMainActivity.this).load(url).into(thub);
        Picasso.with(YoutubeMainActivity.this).load(url1).into(thub1);
        txtTitle.setText(Config.TITLE_VIDEO);
        txtTitle1.setText(Config.TITLE_VIDEO_1);

        thub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
        // Khi click vào icon video thì sẽ hiện tới trang xem video
            Intent intent = new Intent(YoutubeMainActivity.this, PlayerActivity.class);
            startActivity(intent);
            }
        });
        thub1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
        // Khi click vào icon video thì sẽ hiện tới trang xem video
            Intent intent = new Intent(YoutubeMainActivity.this, PlayerActivity.class);
            startActivity(intent);
            }
        });
    }
}
