package scores;

import interactors.base.Interactor;
import review.Result;

public interface AddScoreInteractor extends Interactor {

    void setCallback(AddScoreInteractor.Callback callback);
    void setResult(Result result);

    interface Callback {
        void onScoreAdded(Result result);
    }

}