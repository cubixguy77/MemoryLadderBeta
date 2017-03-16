package selectChallenge.viewChallengeList;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import selectChallenge.getChallengeList.GetChallengeListInteractor;
import selectChallenge.viewChallengeCard.Challenge;
import injection.PerActivity;

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
        System.out.println("Presenter: Requesting challenges");
        getChallengeListInteractor.execute();
    }

    @Override
    public void resume() {
        getChallengeListInteractor.setCallback(this);
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getChallengeListInteractor.setCallback(null);
        this.view = null;
    }

    @Override
    public void onChallengeListLoaded(List<Challenge> challenges) {
        System.out.println("Presenter: Challenges received");
        view.renderChallengeList(challenges);
    }
}
