package speednumbers.mastersofmemory.challengelist.challenge.settings.update;

import javax.inject.Inject;

import framework.executor.Executor;
import framework.executor.MainThread;
import speednumbers.mastersofmemory.challengelist.challenge.settings.Setting;
import database.repository.IRepository;
import framework.interactors.AbstractInteractor;

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
