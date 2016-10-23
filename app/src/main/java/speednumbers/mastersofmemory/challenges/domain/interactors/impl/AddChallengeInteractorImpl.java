package speednumbers.mastersofmemory.challenges.domain.interactors.impl;

import javax.inject.Inject;

import executor.Executor;
import executor.MainThread;
import speednumbers.mastersofmemory.challenges.domain.interactors.AddChallengeInteractor;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import repository.IRepository;
import interactors.base.AbstractInteractor;
import speednumbers.mastersofmemory.challenges.domain.model.ChallengeFactory;

public class AddChallengeInteractorImpl extends AbstractInteractor implements AddChallengeInteractor {

    @Inject public AddChallengeInteractor.Callback mCallback;
    private IRepository mRepository;
    private long gameKey;
    private int numDigits;

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
    public void setModel(int numDigits) {
        this.numDigits = numDigits;
    }

    @Override
    public void run() {
        System.out.println("Interactor: Requesting challenge addition");
        mRepository.insertChallenge(ChallengeFactory.CreateChallenge(gameKey, numDigits), new IRepository.AddedChallengeCallback() {
            @Override
            public void onChallengeAdded(Challenge challenge) {
                mCallback.onChallengeAdded(challenge);
            }
        });
    }
}
