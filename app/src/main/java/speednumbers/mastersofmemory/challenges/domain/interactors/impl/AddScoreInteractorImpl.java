package speednumbers.mastersofmemory.challenges.domain.interactors.impl;

import javax.inject.Inject;

import executor.Executor;
import executor.MainThread;
import interactors.base.AbstractInteractor;
import repository.IRepository;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.interactors.AddScoreInteractor;

public class AddScoreInteractorImpl extends AbstractInteractor implements AddScoreInteractor {

    private IRepository mRepository;
    private Result result;
    private AddScoreInteractor.Callback callback;

    @Inject
    public AddScoreInteractorImpl(Executor threadExecutor, MainThread mainThread, IRepository repository) {
        super(threadExecutor, mainThread);
        mRepository = repository;
    }

    @Override
    public void run() {
        System.out.println("Interactor: Insert Score");
        mRepository.insertScore(result, new IRepository.InsertScoreCallback() {
            @Override
            public void onScoreAdded(Result result) {
                callback.onScoreAdded(result);
            }
        });
    }

    @Override
    public void setCallback(AddScoreInteractor.Callback callback) {
        this.callback = callback;
    }

    @Override
    public void setResult(Result result) {
        this.result = result;
    }
}