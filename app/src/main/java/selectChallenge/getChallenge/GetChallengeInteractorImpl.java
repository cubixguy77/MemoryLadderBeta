package selectChallenge.getChallenge;

import javax.inject.Inject;

import framework.executor.Executor;
import framework.executor.MainThread;
import framework.interactors.AbstractInteractor;
import database.repository.IRepository;
import selectChallenge.viewChallengeCard.Challenge;

public class GetChallengeInteractorImpl extends AbstractInteractor implements GetChallengeInteractor {

    @Inject
    public Callback mCallback;
    private IRepository mRepository;
    private long challengeKey;

    @Inject
    public GetChallengeInteractorImpl(long challengeKey, Executor threadExecutor, MainThread mainThread, IRepository repository) {
        super(threadExecutor, mainThread);
        this.challengeKey = challengeKey;
        mRepository = repository;
    }

    @Override
    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void run() {
        System.out.println("Interactor: Requesting challenge of key: " + challengeKey);
        mRepository.getChallenge(challengeKey, new IRepository.GetChallengeCallback() {
            @Override
            public void onChallengeLoaded(Challenge challenge) {
                mCallback.onChallengeLoaded(challenge);
            }
        });
    }
}
