package injection.modules;

import dagger.Module;
import dagger.Provides;
import injection.PerActivity;
import executor.Executor;
import executor.MainThread;
import speednumbers.mastersofmemory.challenges.domain.interactors.impl.UpdateSettingInteractorImpl;
import repository.IRepository;
import speednumbers.mastersofmemory.challenges.domain.interactors.UpdateSettingInteractor;

@Module
public class SettingModule {

  public SettingModule() { }

  @Provides
  @PerActivity
  UpdateSettingInteractor provideUpdateSettingInteractor(Executor threadExecutor, MainThread mainThread, IRepository repository) {
    return new UpdateSettingInteractorImpl(threadExecutor, mainThread, repository);
  }
}