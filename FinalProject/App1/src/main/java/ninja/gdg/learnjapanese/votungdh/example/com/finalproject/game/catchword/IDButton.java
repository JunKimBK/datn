package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.game.catchword;

/**
 * Created by VoTungDH on 17/03/31.
 */
public class IDButton {
    private int idAnwser, idPick;

    public IDButton(int idPick, int idAnwser) {
        this.idAnwser = idAnwser;
        this.idPick = idPick;
    }

    public int getIdPick() {
        return idPick;
    }

    public int getIdAnwser() {
        return idAnwser;
    }
}
