package framework.injection.components;

import android.content.Context;

import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Singleton;

import dagger.Component;
import framework.injection.modules.ApplicationModule;
import framework.executor.Executor;
import framework.executor.MainThread;
import database.repository.IRepository;
import selectChallenge.viewChallengeList.BaseActivity;
import playChallenge.BaseActivityChallenge;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);
  void inject(BaseActivityChallenge baseActivity);

  //Exposed to sub-graphs.
  Context context();
  Executor executor();
  MainThread mainThread();
  ThreadPoolExecutor threadPoolExecutor();
  IRepository repository();
}
