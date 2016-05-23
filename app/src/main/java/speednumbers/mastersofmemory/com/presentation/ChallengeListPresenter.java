package speednumbers.mastersofmemory.com.presentation;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import speednumbers.mastersofmemory.com.domain.interactors.GetChallengeListInteractor;
import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.presentation.injection.PerActivity;

@PerActivity
public class ChallengeListPresenter implements IChallengeListPresenter, GetChallengeListInteractor.Callback {

    private IChallengeListView view;
    private GetChallengeListInteractor getChallengeListInteractor;

    @Inject
    public ChallengeListPresenter(@Named("challengeList") GetChallengeListInteractor getChallengeListInteractor) {
        this.getChallengeListInteractor = getChallengeListInteractor;
    }

    @Override
    public void setView(IChallengeListView view) {
        this.view = view;
    }

    @Override
    public void loadChallengeList() {
        getChallengeListInteractor.execute();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onChallengeListLoaded(List<Challenge> challenges) {
        view.renderChallengeList(challenges);
    }
}
