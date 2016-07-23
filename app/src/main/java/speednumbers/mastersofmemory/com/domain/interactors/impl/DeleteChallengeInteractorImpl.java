package speednumbers.mastersofmemory.com.domain.interactors.impl;

import javax.inject.Inject;

import speednumbers.mastersofmemory.com.domain.executor.Executor;
import speednumbers.mastersofmemory.com.domain.executor.MainThread;
import speednumbers.mastersofmemory.com.domain.interactors.DeleteChallengeInteractor;
import speednumbers.mastersofmemory.com.domain.interactors.UpdateSettingInteractor;
import speednumbers.mastersofmemory.com.domain.interactors.base.AbstractInteractor;
import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.domain.model.Setting;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;

public class DeleteChallengeInteractorImpl extends AbstractInteractor implements DeleteChallengeInteractor {

    private IRepository mRepository;
    private Challenge challenge;

    @Inject
    public DeleteChallengeInteractorImpl(Executor threadExecutor, MainThread mainThread, IRepository repository) {
        super(threadExecutor, mainThread);
        mRepository = repository;
    }

    @Override
    public void run() {
        System.out.println("Interactor: deleting challenge");
        mRepository.deleteChallenge(challenge);
    }

    @Override
    public void deleteChallenge(Challenge challenge) {
        this.challenge = challenge;
        this.execute();
    }
}
