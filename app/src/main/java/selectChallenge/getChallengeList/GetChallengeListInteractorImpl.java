package selectChallenge.getChallengeList;

import java.util.List;

import javax.inject.Inject;

import framework.executor.Executor;
import framework.executor.MainThread;
import framework.interactors.AbstractInteractor;
import selectChallenge.viewChallengeCard.Challenge;
import speednumbers.mastersofmemory.challengelist.challenge.settings.Setting;
import database.repository.IRepository;

public class GetChallengeListInteractorImpl extends AbstractInteractor implements GetChallengeListInteractor {

    @Inject
    public GetChallengeListInteractor.Callback mCallback;
    private IRepository mRepository;
    private long gameKey;

    @Inject
    public GetChallengeListInteractorImpl(long gameKey, Executor threadExecutor, MainThread mainThread, IRepository repository) {
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
                System.out.println("Interactor: " + challenges.size() + " Challenges Received");


                for (final Challenge challenge : challenges) {
                    long challengeKey = challenge.getChallengeKey();
                    mRepository.getSettingsList(challengeKey, new IRepository.GetSettingsCallback() {
                        @Override
                        public void onSettingsLoaded(List<Setting> settings) {
                            System.out.println("Interactor: " + settings.size() + " Settings Received");
                            challenge.setSettings(settings);
                        }
                    });
                }

                mCallback.onChallengeListLoaded(challenges);
            }
        });
    }
}
