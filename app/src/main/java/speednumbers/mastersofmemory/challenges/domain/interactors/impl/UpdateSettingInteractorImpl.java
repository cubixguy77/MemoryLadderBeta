package speednumbers.mastersofmemory.challenges.domain.interactors.impl;

import javax.inject.Inject;

import executor.Executor;
import executor.MainThread;
import speednumbers.mastersofmemory.challenges.domain.model.Setting;
import repository.IRepository;
import speednumbers.mastersofmemory.challenges.domain.interactors.UpdateSettingInteractor;
import interactors.base.AbstractInteractor;

public class UpdateSettingInteractorImpl extends AbstractInteractor implements UpdateSettingInteractor {

    private IRepository mRepository;
    private Setting setting;

    @Inject
    public UpdateSettingInteractorImpl(Executor threadExecutor, MainThread mainThread, IRepository repository) {
        super(threadExecutor, mainThread);
        mRepository = repository;
    }

    @Override
    public void run() {
        System.out.println("Interactor: Updating setting");
        mRepository.updateSetting(setting);
    }

    @Override
    public void updateSetting(Setting setting) {
        this.setting = setting;
        this.execute();
    }
}
