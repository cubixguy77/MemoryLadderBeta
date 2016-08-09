package injection.components;

import dagger.Component;
import injection.PerActivity;
import injection.modules.ActivityModule;
import injection.modules.ChallengeModule;
import memorization.NumberMemoryActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ChallengeModule.class})
public interface ChallengeComponent extends ActivityComponent {
  void inject(NumberMemoryActivity numberMemoryActivity);
}
