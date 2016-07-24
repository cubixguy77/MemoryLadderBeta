package injection.components;

import dagger.Component;
import speednumbers.mastersofmemory.challenges.presentation.activities.ChallengeListActivity;
import speednumbers.mastersofmemory.challenges.presentation.fragments.ChallengeListFragment;
import injection.PerActivity;
import injection.modules.ActivityModule;
import injection.modules.ChallengeModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ChallengeModule.class})
public interface ChallengeComponent extends ActivityComponent {
  void inject(ChallengeListFragment challengeListFragment);
  void inject(ChallengeListActivity challengeListActivity);
  //void inject(GetChallengeListInteractorImpl getChallengeListInteractor);
  //void inject(GetChallengeListInteractorImpl getChallengeListInteractor);
  //GetChallengeListInteractor.Callback getChallengeListInteractor();
}
