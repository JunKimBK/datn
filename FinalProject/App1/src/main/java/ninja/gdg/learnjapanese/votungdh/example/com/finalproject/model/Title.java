package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.model;

/**
 * Created by VoTungDH on 17/05/02.
 */

public class Title {
    private int idtitle;
    private String title;
    private String path;
    private String meaning;

    public int getIdtitle() {
        return idtitle;
    }

    public void setIdtitle(int idtitle) {
        this.idtitle = idtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public Title(int idtitle, String title, String path, String meaning) {
        this.idtitle = idtitle;
        this.title = title;
        this.path = path;
        this.meaning = meaning;
    }
}
