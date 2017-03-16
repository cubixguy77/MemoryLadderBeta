package framework.injection.components;

import dagger.Component;
import framework.injection.PerActivity;
import framework.injection.modules.ActivityModule;
import framework.injection.modules.ChallengeModule;
import playChallenge.writtenNumbersChallenge.memorization.NumberMemoryActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ChallengeModule.class})
public interface ChallengeComponent extends ActivityComponent {
  void inject(NumberMemoryActivity numberMemoryActivity);
}
