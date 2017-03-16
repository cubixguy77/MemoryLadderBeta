package scores.viewScoreList;

import java.util.Date;

public class Score {

    private int rank;
    private int score;
    private float memTime;
    private Date date;

    public Score(int rank, int score, float memTime, Date date) {
        this.rank = rank;
        this.score = score;
        this.memTime = memTime;
        this.date = date;
    }

    int getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }

    float getMemTime() {
        return memTime;
    }
}