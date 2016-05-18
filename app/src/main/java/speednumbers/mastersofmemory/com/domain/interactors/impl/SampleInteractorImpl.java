package speednumbers.mastersofmemory.com.domain.interactors.impl;

import speednumbers.mastersofmemory.com.domain.executor.Executor;
import speednumbers.mastersofmemory.com.domain.executor.MainThread;
import speednumbers.mastersofmemory.com.domain.interactors.SampleInteractor;
import speednumbers.mastersofmemory.com.domain.interactors.base.AbstractInteractor;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class SampleInteractorImpl extends AbstractInteractor implements SampleInteractor {

    private SampleInteractor.Callback mCallback;
    private IRepository                mRepository;

    public SampleInteractorImpl(Executor threadExecutor,
                                MainThread mainThread,
                                Callback callback, IRepository repository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
    }

    @Override
    public void run() {
        // TODO: Implement this with your business logic
    }
}
