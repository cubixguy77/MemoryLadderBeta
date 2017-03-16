package selectChallenge.viewChallengeList;

interface IChallengeListPresenter extends IPresenter {
    void setView(IChallengeListView view);
    void loadChallengeList();
}
