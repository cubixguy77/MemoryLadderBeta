package injection.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import injection.PerActivity;
import executor.Executor;
import executor.MainThread;
import selectChallenge.addChallenge.AddChallengeInteractor;
import selectChallenge.deleteChallenge.DeleteChallengeInteractor;
import selectChallenge.getChallengeList.GetChallengeListInteractor;
import speednumbers.mastersofmemory.challengelist.challenge.settings.update.UpdateSettingInteractor;
import selectChallenge.addChallenge.AddChallengeInteractorImpl;
import selectChallenge.getChallengeList.GetChallengeListInteractorImpl;
import speednumbers.mastersofmemory.challengelist.challenge.settings.update.UpdateSettingInteractorImpl;
import repository.IRepository;
import selectChallenge.deleteChallenge.DeleteChallengeInteractorImpl;
import selectChallenge.viewChallengeList.ChallengeListPresenter;

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