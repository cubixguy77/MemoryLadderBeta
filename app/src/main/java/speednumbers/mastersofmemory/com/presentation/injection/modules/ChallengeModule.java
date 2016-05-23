package speednumbers.mastersofmemory.com.presentation.injection.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import speednumbers.mastersofmemory.com.domain.executor.Executor;
import speednumbers.mastersofmemory.com.domain.executor.MainThread;
import speednumbers.mastersofmemory.com.domain.interactors.GetChallengeListInteractor;
import speednumbers.mastersofmemory.com.domain.interactors.impl.GetChallengeListInteractorImpl;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;
import speednumbers.mastersofmemory.com.presentation.ChallengeListPresenter;
import speednumbers.mastersofmemory.com.presentation.injection.PerActivity;

@Module
public class ChallengeModule {

  private int gameKey;

  public ChallengeModule() {}
  public ChallengeModule(int gameKey) {
    this.gameKey = gameKey;
  }

  @Provides
  @PerActivity
  @Named("challengeList")
  GetChallengeListInteractor provideGetChallengeListInteractor(Executor threadExecutor, MainThread mainThread, GetChallengeListInteractor.Callback callback, IRepository repository) {
    return new GetChallengeListInteractorImpl(threadExecutor, mainThread, callback, repository);
  }

  @Provides
  @PerActivity
  GetChallengeListInteractor.Callback provideCallback(ChallengeListPresenter presenter) {
    return presenter;
  }
}