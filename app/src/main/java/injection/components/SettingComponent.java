package injection.components;

import dagger.Component;
import speednumbers.mastersofmemory.challengelist.challenge.settings.BaseSettingView;
import injection.PerActivity;
import injection.modules.SettingModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {SettingModule.class})
public interface SettingComponent {
  void inject(BaseSettingView baseSettingView);
}
