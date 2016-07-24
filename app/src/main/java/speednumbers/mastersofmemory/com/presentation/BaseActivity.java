package speednumbers.mastersofmemory.com.presentation;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import speednumbers.mastersofmemory.com.MyApplication;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;
import speednumbers.mastersofmemory.com.presentation.injection.HasComponent;
import speednumbers.mastersofmemory.com.presentation.injection.components.ApplicationComponent;
import speednumbers.mastersofmemory.com.presentation.injection.components.ChallengeComponent;
import speednumbers.mastersofmemory.com.presentation.injection.modules.ActivityModule;

/**
 * Base {@link Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity implements HasComponent<ChallengeComponent> {

  @Inject IRepository repo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
  }

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  protected void addFragment(int containerViewId, android.support.v4.app.Fragment fragment) {
    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment, "ChallengeListFragment");
    fragmentTransaction.commit();
  }

  protected ApplicationComponent getApplicationComponent() {
    return ((MyApplication)getApplication()).getApplicationComponent();
  }

  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }
}
