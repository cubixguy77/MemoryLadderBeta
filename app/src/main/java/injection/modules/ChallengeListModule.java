package injection.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import injection.PerActivity;
import executor.Executor;
import executor.MainThread;
import speednumbers.mastersofmemory.challenges.domain.interactors.AddChallengeInteractor;
import speednumbers.mastersofmemory.challenges.domain.interactors.DeleteChallengeInteractor;
import speednumbers.mastersofmemory.challenges.domain.interactors.GetChallengeListInteractor;
import speednumbers.mastersofmemory.challenges.domain.interactors.UpdateSettingInteractor;
import speednumbers.mastersofmemory.challenges.domain.interactors.impl.AddChallengeInteractorImpl;
import speednumbers.mastersofmemory.challenges.domain.interactors.impl.GetChallengeListInteractorImpl;
import speednumbers.mastersofmemory.challenges.domain.interactors.impl.UpdateSettingInteractorImpl;
import repository.IRepository;
import speednumbers.mastersofmemory.challenges.domain.interactors.impl.DeleteChallengeInteractorImpl;
import speednumbers.mastersofmemory.challenges.presentation.presenters.ChallengeListPresenter;

@Module
public class ChallengeListModule {

  private long gameKey;

  public ChallengeListModule(long gameKey) {
    this.gameKey = gameKey;
  }

  @Provides
  @PerActivity
  @Named("challengeList")
  GetChallengeListInteractor provideGetChallengeListInteractor(Executor threadExecutor, MainThread mainThread, IRepository repository) {
    return new GetChallengeListInteractorImpl(gameKey, threadExecutor, mainThread, repository);
  }

  @Provides
  @PerActivity
  DeleteChallengeInteractor provideDeleteChallengeInteractor(Executor threadExecutor, MainThread mainThread, IRepository repository) {
    return new DeleteChallengeInteractorImpl(threadExecutor, mainThread, repository);
  }

  @Provides
  @PerActivity
  AddChallengeInteractor provideAddChallengeInteractor(Executor threadExecutor, MainThread mainThread, IRepository repository) {
    return new AddChallengeInteractorImpl(gameKey, threadExecutor, mainThread, repository);
  }

  @Provides
  @PerActivity
  GetChallengeListInteractor.Callback provideCallback(ChallengeListPresenter presenter) {
    return presenter;
  }

  @Provides
  @PerActivity
  UpdateSettingInteractor provideUpdateSettingInteractor(Executor threadExecutor, MainThread mainThread, IRepository repository) {
    return new UpdateSettingInteractorImpl(threadExecutor, mainThread, repository);
  }
}