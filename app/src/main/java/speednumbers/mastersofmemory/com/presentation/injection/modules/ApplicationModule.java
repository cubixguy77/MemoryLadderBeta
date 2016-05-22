package speednumbers.mastersofmemory.com.presentation.injection.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import speednumbers.mastersofmemory.com.MyApplication;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;
import speednumbers.mastersofmemory.com.storage.DatabaseAPI;
import speednumbers.mastersofmemory.com.storage.DatabaseHelper;
import speednumbers.mastersofmemory.com.storage.Repository;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final MyApplication application;

  public ApplicationModule(MyApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton Context provideApplicationContext() {
    return this.application;
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
