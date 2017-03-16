package scores.getScoreList;

import java.util.List;

import framework.interactors.Interactor;
import scores.viewScoreList.Score;

public interface GetScoreListInteractor extends Interactor {

    void setCallback(GetScoreListInteractor.Callback callback);

    interface Callback {
        void onScoresLoaded(List<Score> scores);
    }
}