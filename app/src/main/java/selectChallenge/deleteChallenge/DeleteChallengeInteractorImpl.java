package selectChallenge.deleteChallenge;

import javax.inject.Inject;

import framework.executor.Executor;
import framework.executor.MainThread;
import framework.interactors.AbstractInteractor;
import selectChallenge.viewChallengeCard.Challenge;
import database.repository.IRepository;

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
