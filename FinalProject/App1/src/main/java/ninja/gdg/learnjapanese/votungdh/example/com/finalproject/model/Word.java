package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.model;


/**
 * Created by VoTungDH on 17/05/02.
 */

public class Word {
    private int idword;
    private int idtitle;
    private String word;
    private String meaning;
    private String picture;
    private String sound;

    public int getIdword() {
        return idword;
    }

    public void setIdword(int idword) {
        this.idword = idword;
    }

    public int getIdtitle() {
        return idtitle;
    }

    public void setIdtitle(int idtitle) {
        this.idtitle = idtitle;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Word(int idword, int idtitle, String word, String meaning, String picture, String sound) {
        this.idword = idword;
        this.idtitle = idtitle;
        this.word = word;
        this.meaning = meaning;
        this.picture = picture;
        this.sound = sound;
    }
}
