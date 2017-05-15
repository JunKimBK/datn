package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.model.Title;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.model.Word;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by VoTungDH on 17/05/06.
 */

public class OkhttpRequest {
    OkHttpClient client = new OkHttpClient();

    public OkhttpRequest() throws IOException {
    }

    String doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public void getjacksonTitle(final Callable<List<Title>> call) throws IOException {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                GsonBuilder gsonBuilder = new GsonBuilder();
                String getjson = null;
                try {
                    getjson = doGetRequest(Config.HOST_TITLE_SERVLET);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gs = gsonBuilder.create();
                List<Title> titles = new ArrayList<Title>();
                titles = Arrays.asList(gs.fromJson(getjson, Title[].class));
                call.next(titles);
            }
        });
        th.start();
    }

    public void getjacksonWord(final Callable<List<Word>> call) throws IOException {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                GsonBuilder gsonBuilder = new GsonBuilder();
                String getjson = null;
                try {
                    getjson = doGetRequest(Config.HOST_WORD_SERVLET);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gs = gsonBuilder.create();
                List<Word> words = new ArrayList<Word>();
                words = Arrays.asList(gs.fromJson(getjson, Word[].class));
                call.next(words);
            }
        });
        th.start();
    }
}
