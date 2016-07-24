package speednumbers.mastersofmemory.challenges.presentation.presenters;

import speednumbers.mastersofmemory.challenges.presentation.views.IChallengeListView;

public interface IChallengeListPresenter extends IPresenter {

    public void setView(IChallengeListView view);
    public void loadChallengeList();
}
