package scores.getScoreList;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import framework.executor.Executor;
import framework.executor.MainThread;
import framework.interactors.AbstractInteractor;
import database.repository.IRepository;
import scores.viewScoreList.Score;

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
        Log.d("GetScoreListInteractor", "Requesting score list for challenge: " + this.challengeKey);
        mRepository.getScoreList(challengeKey, new IRepository.GetScoresCallback() {
            @Override
            public void onScoresLoaded(List<Score> scores) {
                System.out.println("Interactor: " + scores.size() + " Scores Received");
                mCallback.onScoresLoaded(scores);
            }
        });
    }
}