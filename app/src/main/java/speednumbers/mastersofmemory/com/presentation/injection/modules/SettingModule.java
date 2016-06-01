package speednumbers.mastersofmemory.com.presentation.injection.modules;

import dagger.Module;
import dagger.Provides;
import speednumbers.mastersofmemory.com.domain.executor.Executor;
import speednumbers.mastersofmemory.com.domain.executor.MainThread;
import speednumbers.mastersofmemory.com.domain.interactors.UpdateSettingInteractor;
import speednumbers.mastersofmemory.com.domain.interactors.impl.UpdateSettingInteractorImpl;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;
import speednumbers.mastersofmemory.com.presentation.injection.PerActivity;

@Module
public class SettingModule {

  public SettingModule() { }

  @Provides
  @PerActivity
  UpdateSettingInteractor provideUpdateSettingInteractor(Executor threadExecutor, MainThread mainThread, IRepository repository) {
    return new UpdateSettingInteractorImpl(threadExecutor, mainThread, repository);
  }
}