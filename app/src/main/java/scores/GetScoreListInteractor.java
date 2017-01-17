package scores;

import java.util.List;

import interactors.base.Interactor;

public interface GetScoreListInteractor extends Interactor {

    void setCallback(GetScoreListInteractor.Callback callback);

    interface Callback {
        void onScoresLoaded(List<Score> scores);
    }
}