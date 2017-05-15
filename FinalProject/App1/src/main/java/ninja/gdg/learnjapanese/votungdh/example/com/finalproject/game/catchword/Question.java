package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.game.catchword;

/**
 * Created by VoTungDH on 17/03/31.
 */
public class Question {
    private int id;
    private String content;

    public Question(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContext() {
        return content;
    }
}
