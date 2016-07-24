package speednumbers.mastersofmemory.com.domain.interactors.impl;

import javax.inject.Inject;

import speednumbers.mastersofmemory.com.domain.executor.Executor;
import speednumbers.mastersofmemory.com.domain.executor.MainThread;
import speednumbers.mastersofmemory.com.domain.interactors.AddChallengeInteractor;
import speednumbers.mastersofmemory.com.domain.interactors.base.AbstractInteractor;
import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;
import speednumbers.mastersofmemory.com.presentation.ChallengeFactory;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class AddChallengeInteractorImpl extends AbstractInteractor implements AddChallengeInteractor {

    @Inject public AddChallengeInteractor.Callback mCallback;
    private IRepository mRepository;
    private long gameKey;

    @Inject
    public AddChallengeInteractorImpl(long gameKey, Executor threadExecutor, MainThread mainThread, IRepository repository) {
        super(threadExecutor, mainThread);
        this.gameKey = gameKey;
        mRepository = repository;
    }

    @Override
    public void setCallback(AddChallengeInteractor.Callback callback) {
        mCallback = callback;
    }

    @Override
    public void run() {
        System.out.println("Interactor: Requesting challenge addition");
        mRepository.insertChallenge(ChallengeFactory.CreateChallenge(gameKey), new IRepository.AddedChallengeCallback() {
            @Override
            public void onChallengeAdded(Challenge challenge) {
                mCallback.onChallengeAdded(challenge);
            }
        });
    }
}
