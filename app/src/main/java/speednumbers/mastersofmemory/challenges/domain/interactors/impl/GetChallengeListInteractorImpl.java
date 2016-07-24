package speednumbers.mastersofmemory.challenges.domain.interactors.impl;

import java.util.List;

import javax.inject.Inject;

import executor.Executor;
import executor.MainThread;
import speednumbers.mastersofmemory.challenges.domain.interactors.GetChallengeListInteractor;
import interactors.base.AbstractInteractor;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.domain.model.Setting;
import repository.IRepository;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
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
