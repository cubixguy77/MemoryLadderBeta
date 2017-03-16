package selectChallenge.addChallenge;

import javax.inject.Inject;

import executor.Executor;
import executor.MainThread;
import selectChallenge.viewChallengeCard.ChallengeFactory;
import selectChallenge.viewChallengeCard.Challenge;
import repository.IRepository;
import interactors.base.AbstractInteractor;

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
