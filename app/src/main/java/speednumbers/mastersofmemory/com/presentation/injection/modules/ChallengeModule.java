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

  private long gameKey;


  public ChallengeModule(int gameKey) {
    this.gameKey = gameKey;
  }

  @Provides
  @PerActivity
  @Named("challengeList")
  //GetChallengeListInteractor provideGetChallengeListInteractor(GetChallengeListInteractorImpl impl) {
  //  return impl;
  //}
  GetChallengeListInteractor provideGetChallengeListInteractor(Executor threadExecutor, MainThread mainThread, IRepository repository) {
    return new GetChallengeListInteractorImpl(gameKey, threadExecutor, mainThread, repository);
  }

  @Provides
  @PerActivity
  GetChallengeListInteractor.Callback provideCallback(ChallengeListPresenter presenter) {
    return presenter;
  }
}