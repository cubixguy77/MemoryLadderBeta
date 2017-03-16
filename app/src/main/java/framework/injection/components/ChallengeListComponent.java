package framework.injection.components;

import dagger.Component;
import selectChallenge.viewChallengeList.ChallengeListActivity;
import selectChallenge.viewChallengeList.ChallengeListFragment;
import framework.injection.PerActivity;
import framework.injection.modules.ActivityModule;
import framework.injection.modules.ChallengeListModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ChallengeListModule.class})
public interface ChallengeListComponent extends ActivityComponent {
  void inject(ChallengeListFragment challengeListFragment);
  void inject(ChallengeListActivity challengeListActivity);
}
