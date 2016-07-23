package speednumbers.mastersofmemory.com.presentation.injection.components;

import dagger.Component;
import speednumbers.mastersofmemory.com.domain.interactors.GetChallengeListInteractor;
import speednumbers.mastersofmemory.com.domain.interactors.impl.GetChallengeListInteractorImpl;
import speednumbers.mastersofmemory.com.presentation.ChallengeListActivity;
import speednumbers.mastersofmemory.com.presentation.ChallengeListFragment;
import speednumbers.mastersofmemory.com.presentation.injection.PerActivity;
import speednumbers.mastersofmemory.com.presentation.injection.modules.ActivityModule;
import speednumbers.mastersofmemory.com.presentation.injection.modules.ApplicationModule;
import speednumbers.mastersofmemory.com.presentation.injection.modules.ChallengeModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ChallengeModule.class})
public interface ChallengeComponent extends ActivityComponent {
  void inject(ChallengeListFragment challengeListFragment);
  void inject(ChallengeListActivity challengeListActivity);
  //void inject(GetChallengeListInteractorImpl getChallengeListInteractor);
  //void inject(GetChallengeListInteractorImpl getChallengeListInteractor);
  //GetChallengeListInteractor.Callback getChallengeListInteractor();
}
