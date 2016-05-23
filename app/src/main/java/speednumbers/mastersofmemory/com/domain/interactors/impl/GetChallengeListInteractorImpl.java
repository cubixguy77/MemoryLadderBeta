package speednumbers.mastersofmemory.com.domain.interactors.impl;

import javax.inject.Inject;

import speednumbers.mastersofmemory.com.domain.executor.Executor;
import speednumbers.mastersofmemory.com.domain.executor.MainThread;
import speednumbers.mastersofmemory.com.domain.interactors.GetChallengeListInteractor;
import speednumbers.mastersofmemory.com.domain.interactors.base.AbstractInteractor;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class GetChallengeListInteractorImpl extends AbstractInteractor implements GetChallengeListInteractor {

    private GetChallengeListInteractor.Callback mCallback;
    private IRepository                mRepository;

    @Inject
    public GetChallengeListInteractorImpl(Executor threadExecutor, MainThread mainThread, GetChallengeListInteractor.Callback callback, IRepository repository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
    }

    @Override
    public void run() {
        // TODO: Implement this with your business logic
    }
}
