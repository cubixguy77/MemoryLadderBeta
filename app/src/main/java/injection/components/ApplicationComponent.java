package injection.components;

import android.content.Context;

import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Singleton;

import dagger.Component;
import injection.modules.ApplicationModule;
import executor.Executor;
import executor.MainThread;
import repository.IRepository;
import speednumbers.mastersofmemory.challenges.presentation.activities.BaseActivity;
import speednumbers.mastersofmemory.challenges.presentation.activities.BaseActivityChallenge;

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
