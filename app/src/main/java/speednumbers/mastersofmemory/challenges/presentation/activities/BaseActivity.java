package speednumbers.mastersofmemory.challenges.presentation.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import injection.components.ChallengeListComponent;
import repository.IRepository;
import speednumbers.mastersofmemory.challenges.MyApplication;
import injection.HasComponent;
import injection.components.ApplicationComponent;
import injection.modules.ActivityModule;

/**
 * Base {@link Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity implements HasComponent<ChallengeListComponent> {

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
