package injection.components;

import dagger.Component;
import memorization.NumberMemoryActivity;
import speednumbers.mastersofmemory.challenges.presentation.activities.ChallengeListActivity;
import speednumbers.mastersofmemory.challenges.presentation.fragments.ChallengeListFragment;
import injection.PerActivity;
import injection.modules.ActivityModule;
import injection.modules.ChallengeListModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ChallengeListModule.class})
public interface ChallengeListComponent extends ActivityComponent {
  void inject(ChallengeListFragment challengeListFragment);
  void inject(ChallengeListActivity challengeListActivity);
}
