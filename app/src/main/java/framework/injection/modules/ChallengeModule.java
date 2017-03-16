package framework.injection.modules;

import dagger.Module;
import dagger.Provides;
import framework.executor.Executor;
import framework.executor.MainThread;
import framework.injection.PerActivity;
import database.repository.IRepository;
import scores.addScore.AddScoreInteractor;
import scores.getScoreList.GetScoreListInteractor;
import scores.getScoreList.GetScoreListInteractorImpl;
import selectChallenge.getChallenge.GetChallengeInteractor;
import scores.addScore.AddScoreInteractorImpl;
import selectChallenge.getChallenge.GetChallengeInteractorImpl;

@Module
public class ChallengeModule {

  private long challengeKey;

  public ChallengeModule(long challengeKey) {
    this.challengeKey = challengeKey;
  }

  @Provides
  @PerActivity
  GetChallengeInteractor provideGetChallengeInteractor(Executor threadExecutor, MainThread mainThread, IRepository repository) {
    return new GetChallengeInteractorImpl(challengeKey, threadExecutor, mainThread, repository);
  }

  @Provides
  @PerActivity
  AddScoreInteractor provideAddScoreInteractor(Executor threadExecutor, MainThread mainThread, IRepository repository) {
    return new AddScoreInteractorImpl(threadExecutor, mainThread, repository);
  }

  @Provides
  @PerActivity
  GetScoreListInteractor provideGetScoreListInteractor(Executor threadExecutor, MainThread mainThread, IRepository repository) {
    return new GetScoreListInteractorImpl(challengeKey, threadExecutor, mainThread, repository);
  }
}