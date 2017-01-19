package scores;

import java.util.List;

import javax.inject.Inject;

import executor.Executor;
import executor.MainThread;
import interactors.base.AbstractInteractor;
import repository.IRepository;

public class GetScoreListInteractorImpl extends AbstractInteractor implements GetScoreListInteractor {

    @Inject
    Callback mCallback;
    private IRepository mRepository;
    private long challengeKey;

    @Inject
    public GetScoreListInteractorImpl(long challengeKey, Executor threadExecutor, MainThread mainThread, IRepository repository) {
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
        System.out.println("Interactor: Requesting score list");
        mRepository.getScoreList(challengeKey, new IRepository.GetScoresCallback() {
            @Override
            public void onScoresLoaded(List<Score> scores) {
                System.out.println("Interactor: " + scores.size() + " Scores Received");
                mCallback.onScoresLoaded(scores);
            }
        });
    }
}