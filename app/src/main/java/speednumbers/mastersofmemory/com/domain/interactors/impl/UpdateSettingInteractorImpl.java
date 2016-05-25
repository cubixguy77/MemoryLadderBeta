package speednumbers.mastersofmemory.com.domain.interactors.impl;

import javax.inject.Inject;

import speednumbers.mastersofmemory.com.domain.executor.Executor;
import speednumbers.mastersofmemory.com.domain.executor.MainThread;
import speednumbers.mastersofmemory.com.domain.interactors.UpdateSettingInteractor;
import speednumbers.mastersofmemory.com.domain.interactors.base.AbstractInteractor;
import speednumbers.mastersofmemory.com.domain.model.Setting;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;

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
