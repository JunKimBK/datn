package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.youtube;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R;

/**
 * Created by VoTungDH on 17/03/29.
 */

public class PlayerActivity extends YouTubeFailureRecoveryActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_youtube);

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.player_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(Config.KEY_VIDEO);
            player.cueVideo(Config.KEY_VIDEO_1);
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.player_view);
    }
}
