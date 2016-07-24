package speednumbers.mastersofmemory.challenges.domain.interactors.impl;

import javax.inject.Inject;

import executor.Executor;
import executor.MainThread;
import speednumbers.mastersofmemory.challenges.domain.interactors.DeleteChallengeInteractor;
import interactors.base.AbstractInteractor;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import repository.IRepository;

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
