package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.Callable;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.Config;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.OkhttpRequest;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.model.Word;

/**
 * Created by VoTungDH on 17/04/25.
 */

public class DrawWordActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    Paint paint;
    View view;
    Path path2;
    Bitmap bitmap;
    Canvas canvas;
    Button button;
    ArrayList<String> arrPicture;
    ArrayList<Integer> arrIDTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_word);

        ImageView imgView = (ImageView) findViewById(R.id.imgDraw);
        relativeLayout = (RelativeLayout) findViewById(R.id.rltayout);

        button = (Button)findViewById(R.id.cleanbtn);

        view = new SketchSheetView(DrawWordActivity.this);

        paint = new Paint();

        path2 = new Path();

        try {
            GetImageView(imgView);
        } catch (IOException e) {
            e.printStackTrace();
        }

        relativeLayout.addView(view, new LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT));

        paint.setDither(true);

        paint.setColor(Color.parseColor("#000000"));

        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeJoin(Paint.Join.ROUND);

        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setStrokeWidth(2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path2.reset();
            }
        });
    }
    public void GetImageView(final ImageView imgView) throws IOException{
        arrPicture = new ArrayList<>();
        arrIDTitle = new ArrayList<>();

        OkhttpRequest okhttpRequest = new OkhttpRequest();
        okhttpRequest.getjacksonWord(new Callable<List<Word>>() {
            @Override
            public void next(final List<Word> words) {
            for (Word word : words) {
                if (word.getIdtitle()==1) {
                    arrPicture.add(word.getPicture());
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Random rd = new Random();
                    int x = rd.nextInt(arrPicture.size());
                    Picasso.with(DrawWordActivity.this).load(Config.HOST_PATH_WORD+arrPicture.get(x)).into(imgView);
                }
            });
            }
        });
    }

    class SketchSheetView extends View {
        public SketchSheetView(Context context) {
            super(context);
            bitmap = Bitmap.createBitmap(820, 480, Bitmap.Config.ARGB_4444);
            canvas = new Canvas(bitmap);
            this.setBackgroundColor(Color.WHITE);
        }
        private ArrayList<DrawingClass> DrawingClassArrayList = new ArrayList<DrawingClass>();

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            DrawingClass pathWithPaint = new DrawingClass();
            canvas.drawPath(path2, paint);
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                path2.moveTo(event.getX(), event.getY());
                path2.lineTo(event.getX(), event.getY());
            }
            else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                path2.lineTo(event.getX(), event.getY());
                pathWithPaint.setPath(path2);
                pathWithPaint.setPaint(paint);
                DrawingClassArrayList.add(pathWithPaint);
            }
            invalidate();
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (DrawingClassArrayList.size() > 0) {
                canvas.drawPath(
                    DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPath(),
                    DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPaint());
            }
        }
    }

    public class DrawingClass {
        Path DrawingClassPath;
        Paint DrawingClassPaint;
        public Path getPath() {
            return DrawingClassPath;
        }

        public void setPath(Path path) {
            this.DrawingClassPath = path;
        }

        public Paint getPaint() {
            return DrawingClassPaint;
        }

        public void setPaint(Paint paint) {
            this.DrawingClassPaint = paint;
        }
    }

}