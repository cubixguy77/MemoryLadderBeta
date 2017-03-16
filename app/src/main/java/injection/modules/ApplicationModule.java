package injection.modules;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import selectChallenge.MyApplication;
import executor.Executor;
import executor.MainThread;
import executor.impl.ThreadExecutor;
import repository.IRepository;
import storage.DatabaseAPI;
import storage.DatabaseHelper;
import storage.Repository;
import executor.impl.MainThreadImpl;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final MyApplication application;

  private static final int                     CORE_POOL_SIZE  = 3;
  private static final int                     MAX_POOL_SIZE   = 5;
  private static final int                     KEEP_ALIVE_TIME = 120;
  private static final TimeUnit TIME_UNIT       = TimeUnit.SECONDS;
  private static final BlockingQueue<Runnable> WORK_QUEUE      = new LinkedBlockingQueue<Runnable>();

  public ApplicationModule(MyApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton
  Context provideApplicationContext() {
    return this.application;
  }

  @Provides
  @Singleton
  Executor provideThreadExecutor(ThreadExecutor threadExecutor) {
    return threadExecutor;
  }

  @Provides
  @Singleton
  ThreadPoolExecutor provideThreadPoolExecutor() {
    return new ThreadPoolExecutor(
        CORE_POOL_SIZE,
        MAX_POOL_SIZE,
        KEEP_ALIVE_TIME,
        TIME_UNIT,
        WORK_QUEUE);
  }


  @Provides
  @Singleton
  MainThread provideMainThread() {
    return new MainThreadImpl(new Handler(Looper.getMainLooper()));
  }

  @Provides
  @Singleton
  IRepository provideRepository(Repository repository) {
    return repository;
  }

  @Provides
  @Singleton
  DatabaseAPI provideDatabaseAPI(DatabaseHelper db) {
    return db;
  }
}
