package scores;

import java.util.Date;

public class Score {

    private int rank;
    private int score;
    private int memTime;
    private Date date;

    public Score(int rank, int score, int memTime, Date date) {
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

    int getMemTime() {
        return memTime;
    }
}