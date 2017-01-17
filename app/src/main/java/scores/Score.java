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


    public int getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }

    public int getMemTime() {
        return memTime;
    }

    public Date getDate() {
        return date;
    }
}