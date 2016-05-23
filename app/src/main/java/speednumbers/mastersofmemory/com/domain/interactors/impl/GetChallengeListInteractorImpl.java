package speednumbers.mastersofmemory.com.domain.interactors.impl;

import java.util.List;

import javax.inject.Inject;

import speednumbers.mastersofmemory.com.domain.executor.Executor;
import speednumbers.mastersofmemory.com.domain.executor.MainThread;
import speednumbers.mastersofmemory.com.domain.interactors.GetChallengeListInteractor;
import speednumbers.mastersofmemory.com.domain.interactors.base.AbstractInteractor;
import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class GetChallengeListInteractorImpl extends AbstractInteractor implements GetChallengeListInteractor {

    @Inject
    public GetChallengeListInteractor.Callback mCallback;
    private IRepository                mRepository;
    private int gameKey;

    @Inject
    public GetChallengeListInteractorImpl(int gameKey, Executor threadExecutor, MainThread mainThread, IRepository repository) {
        super(threadExecutor, mainThread);
        this.gameKey = gameKey;
        mRepository = repository;
    }

    @Override
    public void setCallback(GetChallengeListInteractor.Callback callback) {
        mCallback = callback;
    }

    @Override
    public void run() {
        System.out.println("Interactor: Requesting challenges");
        mRepository.getChallengeList(gameKey, new IRepository.GetChallengesCallback() {
            @Override
            public void onChallengesLoaded(List<Challenge> challenges) {
                System.out.println("Interactor: Challenges Received");
                mCallback.onChallengeListLoaded(challenges);
            }
        });
    }
}
