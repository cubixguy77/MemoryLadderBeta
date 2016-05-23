package speednumbers.mastersofmemory.com.presentation.injection.components;

import android.content.Context;

import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Singleton;

import dagger.Component;
import speednumbers.mastersofmemory.com.domain.executor.Executor;
import speednumbers.mastersofmemory.com.domain.executor.MainThread;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;
import speednumbers.mastersofmemory.com.presentation.BaseActivity;
import speednumbers.mastersofmemory.com.presentation.injection.modules.ApplicationModule;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  //Exposed to sub-graphs.
  Context context();
  Executor executor();
  MainThread mainThread();
  ThreadPoolExecutor threadPoolExecutor();
  IRepository repository();
}
