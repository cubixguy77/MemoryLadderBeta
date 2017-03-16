package framework.injection.components;

import dagger.Component;
import framework.injection.modules.SettingModule;
import selectChallenge.viewChallengeCard.challengeSettings.BaseSettingView;
import framework.injection.PerActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {SettingModule.class})
public interface SettingComponent {
  void inject(BaseSettingView baseSettingView);
}
