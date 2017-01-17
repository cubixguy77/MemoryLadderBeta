package injection.modules;

import dagger.Module;
import dagger.Provides;
import executor.Executor;
import executor.MainThread;
import injection.PerActivity;
import repository.IRepository;
import scores.AddScoreInteractor;
import speednumbers.mastersofmemory.challenges.domain.interactors.GetChallengeInteractor;
import scores.AddScoreInteractorImpl;
import speednumbers.mastersofmemory.challenges.domain.interactors.impl.GetChallengeInteractorImpl;

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
}